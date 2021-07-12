# Dyanmic Porxy와 CGlib의 차이점

## 프록시 패턴
Dynamic Proxy와 CGlib은 기존 코드에 변경을 가하지 않으면서 기능을 추가할 수 있는 프록시 기술을 구현하기 위한 방법들입니다.

![image](https://user-images.githubusercontent.com/50797070/125290110-ec8a9900-e35a-11eb-92f8-ba55abaab72f.png)

프록시 패턴의 주요 개념은 동일한 인터페이스를 가진 구현 클래스는 Client에서 사용할때 다형성을 활용하여 동일한 인터페이스에 다른 기능을 정의하여 사용할 수 있는 것입니다.

## Dyanmic Porxy

프록시 패턴을 직접 구현하는 경우에 프록시 클래스를 직접 구현해야하고 코드량이 많아지는 문제점이 있습니다.
이에 자바에서 리플렉션을 활용한 ```Proxy 클래스```를 제공하고 있고 이를 사용하면 런타임시에 동적으로 기능을 추가할 수 있습니다.
```
BookService bookService = (BookService) Proxy.newProxyInstance(BookService.class.getClassLoader(), new Class[]{BookService.class}, 
    new InvocationHandler() {
        BookService bookService = new DefaultBookService();
        @Override
        public Object invoke(Object proxy, Method, method, Object[] args) throws Throwable {
            if (method.getName().equals("rent")) {
                System.out.println("aaaa");
                Object invoke = method.invoke(bookService, args);
                System.out.println("bbbb");
                return invoke;
            }

            return method.invoke(bookService, args);
        }
    });
 ```
해당 방식의 제약사항은 인터페으스를 통해서만 Proxy를 생성할 수 있다는 것입니다.


## CGlib
Dyanmic Porxy의 제약사항을 언급했던거와 같이 클래스의 프록시가 필요한 경우에는 다른 방법은 사용해야 하는데 그 방법 중 하나가 CGlib을 활용하는 것입니다.
이미 스프링이나 하이버네이트에서 내장되어 사용하고 있습니다.

```
MethodInterceptor handler = new MethodInterceptor() {
    BookService bookService = new DefaultBookService();
    @Override
    public Object intercept(Object interceptor, Method, method, Object[] args, MethodProxy methodProxy) throws Throwable {
            if (method.getName().equals("rent")) {
                System.out.println("aaaa");
                Object invoke = method.invoke(bookService, args);
                System.out.println("bbbb");
                return invoke;
            }

            return method.invoke(bookService, args);
        }
    });

BookService bookService = (BookService) Enhancer.create(BookService.class, handler);
```
CGlib의 제약사항은 final 클래스나 메소드는 advise할 수 없다는 것입니다.

## AspectJ
SpringAOP는 AspectJ 스타일로 제공합니다.



