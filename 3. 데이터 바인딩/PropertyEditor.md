# 데이터 바인딩 추상화:PropertyEditor
스프링에서는 사용자가 입력한 값을 타겟 객체에 설정하는 데이터 바인딩 기능을 제공합니다.

org.spirngframework.validation.DataBinder 인터페이스를 통해서 데이터 바인딩 기능을 지원하며, 사용자가 입력한 값을 도메인 모델에 동적으로 변환해서 넣어줍니다.

- 기술적인 관점: 프로퍼티 값을 타겟 객체에 설정하는 기능
- 사용자 관점: 사용자 입력값을 애플리케이션 도메인 모델에 동적으로 변환해 주는 기능
- 해석: 사용자의 입력값은 대부분 '문자열'인데, 그 값을 객체가 가지고 있는 데이터 타입은 int, long, Boolean, Date 입니다.
- 심지어 Event, Book 같은 도메인 타입으로도 변환해서 넣어줍니다.
- 즉, 사용자의 문자열 입력값을 자바의 데이터 타입으로 변환해서 넣어줄 때, 적절하게 데이터를 넣기 위한 데이터 바인딩이 필요합니다.

## PropertyEditor

- 스프링 3.0 이전까지 DataBinder가 변환 작업 사용하던 인터페이스입니다.
- 쓰레드 - 세이프 하지 않음(상태 정보 저장 하고 있음, 따라서 싱글톤 빈으로 등록해서 쓰다가는...)
- Object와 String 간의 변환만 할 수 있어, 사용 범위가 제한적 입니다.

```
public class Event {

    Integer id;
    String title;

    public Event(Integer id) {
        this.id = id;
    }

    public Event(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
```

```
public class EventEditor extends PropertyEditorSupport {

    @Override
    public String getAsText(){
        Event event = (Event)getValue();
        return event.getId().toString();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(new Event(Integer.parseInt(text)));
    }
}
```

```
@RestController
public class EventController {

    @InitBinder
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Event.class, new EventEditor());
    }

    @GetMapping("/event/{event}")
    public String getEvent(@PathVariable Event event){
        System.out.println(event);
        return event.getId().toString();
    }
}
```

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
