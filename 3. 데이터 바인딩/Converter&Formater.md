# 데이터 바인딩 추상화: Converter와 Formatter

## Converter

- S 타입을 T 타입으로 변환할 수 있는 매우 일반적인 변환기
- 상태 정보 없음 == Stateless == 쓰레드세이프
- ConverterRegistry에 등록해서 사용

## Formatter

- PropertyEditor 대체제
- Object와 String 간의 변환을 담당한다
- 문자열을 Locale에 따라 다국화하는 기능도 제공합니다.
- FormatterRegistry에 등록해서 사용

## ConversionServie

- 실제 변환 작업은 이 인터페이스를 통해서 쓰레드-세이프
- 스프링 MVC, 빈 설정, SqEL에서 사용합니다.
- DefaultFormattingConversionService
  - FormatterRegistry
  - ConversionServie
  - 여러 기본 컴버터와 포매터 등록 해줌
