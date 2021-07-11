# 데이터 바인딩 추상화:PropertyEditor

org.spirngframework.validation.DataBinder

- 기술적인 관점: 프로퍼티 값을 타겟 객체에 설정하는 기능
- 사용자 관점: 사용자 입력값을 애플리케이션 도메인 모델에 동적으로 변환해 주는 기능
- 해석: 입력값은 대부분 '문자열'인데, 그 값을 객체가 가지고 있는 int, long, Boolean, Date 등..
- 심지어 Event, Book 같은 도메인 타입으로도 변환해서 넣어주는 기능

## PropertyEditor

- 스프링 3.0 이전까지 DataBinder가 변환 작업 사용하던 인터페이스
- 쓰레드 - 세이프 하지 않음(상태 정보 저장 하고 있음, 따라서 싱글톤 빈으로 등록해서 쓰다가는...)
- Object와 String 간의 변환만 할 수 있어, 사용 범위가 제한적 임
