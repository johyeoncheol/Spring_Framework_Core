# IoC
Inversion of Control : 의존 관계 주입, 
어떤 객체가 사용하는 의존 객체를 직접 만들어 사용하는게 아니라, 주입 받아 사용하는 방법을 말함

## 스프링 IoC 컨테이너
- BeanFactory
- 애플리케이션 컴포넌트의 중앙 저장소
- 빈 설정 소스로 부터 빈 정의를 읽어들이고, 빈을 구성하고 제공한다.

## 빈
- 스프링 IoC 컨테이너가 관리 하는 객체
- 장점
    1. 의존성 관리
    2. 스코프
        
        => 싱글톤: 하나
        => 프로토타입: 매번 다른 객체
        
    3. 라이프 사이클 인터페이스
    
## ApplicationContext
- BeanFactory
- 메시지 소스 처리 기능
- 이벤트 발생 기능
- 리소스 로딩 기능


## docs 정리
BeanFactory 정리
Spring 컨테이너에 액세스하기 위한 루트 인터페이스입니다.

특정 용도로는 Listable BeanFactory 및 Configurable BeanFactory와 같은 추가 인터페이스를 사용할 수 있습니다.

각각의 문자열 이름으로 고유하게 식별되는 여러 개의 bean 정의를 포함하는 개체에 의해 구현됩니다.
Bean 정의에 따라 Factroy에서는 포함된 객체의 독립 인스턴스 또는 단일 공유 인스턴스를 반환합니다.


