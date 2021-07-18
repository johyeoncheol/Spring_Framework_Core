# 데이터 바인딩 추상화: Converter와 Formatter

## Converter

- S 타입을 T 타입으로 변환할 수 있는 매우 일반적인 변환기
- 상태 정보 없음 == Stateless == 쓰레드세이프
- ConverterRegistry에 등록해서 사용

```
public class StringToEventConverter implements Converter<String, Event> {
	@Override
    public Event convert(String source) {
    	Event event = new Event();
        event.setId(Integer.parseInt(source));
        return event;
    }
}
```

## Formatter

- PropertyEditor 대체제
- Object와 String 간의 변환을 담당한다
- 문자열을 Locale에 따라 다국화하는 기능도 제공합니다.
- FormatterRegistry에 등록해서 사용

```
public class EventFormatter implements Formatter<Event> {

    @Override
    public Event parse(String text, Locale locale) throws ParseException {     
        return new Event(Integer.parseInt(text));
    }

    @Override
    public String print(Event event, Locale locale) {
        return event.getId().toString();
    }
}
```
## ConversionServie

- 실제 변환 작업은 이 인터페이스를 통해서 쓰레드-세이프
- 스프링 MVC, 빈 설정, SqEL에서 사용합니다.
- DefaultFormattingConversionService
  - FormatterRegistry
  - ConversionServie
  - 여러 기본 컴버터와 포매터 등록 해줌

- [도큐먼트 참고](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/convert/ConversionService.html)


## 관련 코드 정리
### Converter 등록하기

#### EventConverter
```
public class EventConverter {

    public static class StringToEventConverter implements Converter<String, Event> {
        @Override
        public Event convert(String source) {
            return new Event(Integer.parseInt(source));
        }
    }

    public static class EventToStringConverter implements Converter<Event, String> {
        @Override
        public String convert(Event source) {
            return source.getId().toString();
        }
    }
}
```

#### WebConfig
```
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new EventConverter.StringToEventConverter());
    }
}
```

#### EventController
```
@RestController
public class EventController {
    @GetMapping("/event/{event}")
    public String getEvent(@PathVariable Event event) {
        System.out.println(event);
        return event.getId().toString();
    }
}
```

#### EventControllerTest
```
@RunWith(SpringRunner.class)
@WebMvcTest
public class EventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getTest() throws Exception {
        mockMvc.perform(get("/event/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}
```

### Formatter 등록하기

#### EventFormatter
```
public class EventFormatter implements Formatter<Event> {

    @Override
    public Event parse(String text, Locale locale) throws ParseException {
        return new Event(Integer.parseInt(text));
    }

    @Override
    public String print(Event event, Locale locale) {
        return event.getId().toString();
    }
}
```

#### WebConfig
```
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        //registry.addConverter(new EventConverter.StringToEventConverter());
        registry.addFormatter(new EventFormatter());
    }
}
```

### 스프링 부트에서 사용하는 방법

- WebConfig.java 삭제후 Formatter 또는 Converter를 빈으로 등록만 해주면 자동으로 사용 가능합니다.

#### EventConverter
```
public class EventConverter {
    @Component
    public static class StringToEventConverter implements Converter<String, Event> {
        @Override
        public Event convert(String source) {
            return new Event(Integer.parseInt(source));
        }
    }

    @Component
    public static class EventToStringConverter implements Converter<Event, String> {
        @Override
        public String convert(Event source) {
            return source.getId().toString();
        }
    }
}
```

#### EventFormatter
```
@Component
public class EventFormatter implements Formatter<Event> {

    @Override
    public Event parse(String text, Locale locale) throws ParseException {
        return new Event(Integer.parseInt(text));
    }

    @Override
    public String print(Event event, Locale locale) {
        return event.getId().toString();
    }
}
```

### @WebMvcTest
```
@RunWith(SpringRunner.class)
@WebMvcTest({
        EventFormatter.class,
        EventController.class})
public class EventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getTest() throws Exception {
        mockMvc.perform(get("/event/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}
```

### 등록된 Convert 전부 확인 방법
```
@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    ConversionService conversionService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(conversionService);

        System.out.println(conversionService.getClass().toString());
    }
}
```