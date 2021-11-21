![img.png](img.png)

- ObjectMapper로 Json 직렬화시에 응답 객체안의 객체 속 필드를 객체에서 꺼내서 응답객체에 포함시키고 싶을시 `@JsonUnwrapped`를 사용하면 객체 내부에 포함된 형태에서 꺼내서 직렬화 할 수 있다

![img_1.png](img_1.png)

- 적용전

![img_2.png](img_2.png)

- 적용 후