#okhttp
该包主要封装网络操作，在 retrofit 开源库的基础上增加一层封装。

build.gradle 管理着不同服务器的 baseUri

RetrofitHelper 根据不同的 baseUri 生产不同服务器的 Retrofit 对象

每个服务器对应一个 XXXApi 类，管理着该服务器的所有接口

对每个服务器的接口访问通过 XXXController 加一层封装，在该类里先对数据进行一层处理，再通过自定义的回调接口 RetrofitListener 将
请求结果进行回调

建议与服务器交互的 json 对象实体类命名方式为 XXXXResEntity， XXXReqEntity

该包隐藏内容具体实现，对外开放的权限只有 XXXController 类，外部进行网络操作只需要和 XXXController 交互即可。
