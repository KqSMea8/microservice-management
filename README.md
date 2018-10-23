# microservice-management
微服务治理平台

## 注册管理
#### 1.注册应用列表
URL: /app/list

METHOD: GET

RESPONSE：
```json
[{
		name: "TEST-APP-A",
		instance: [{
				instanceId: "127.0.0.1:8880",
				status: "OUT_OF_SERVICE",
				metadata: {
					@class: "java.util.Collections$EmptyMap"
				}
			},
			{
				instanceId: "127.0.0.2:8880",
				status: "UP",
				metadata: {
					@class: "java.util.Collections$EmptyMap"
				}
			}
		]
	}
]
```
#### 2.应用下实例列表
URL: /instance/list?app={appName}

METHOD: GET

Params：

     appName  应用名称

RESPONSE：
```json
[{
		instanceId: "127.0.0.1:8880",
		status: "OUT_OF_SERVICE",
		metadata: {
			@class: "java.util.Collections$EmptyMap"
		}
	},
	{
		instanceId: "127.0.0.2:8880",
		status: "UP",
		metadata: {
			@class: "java.util.Collections$EmptyMap"
		}
	}
]
```
#### 3.下线实例
URL: /instance/offline?app={appName}&instanceId={instanceId}

METHOD: POST

Params：

       appName        应用名称
       instanceId     实例名称

RESPONSE：
```json
{
	
}
```
#### 4.上线实例
URL: /instance/online?app={appName}&instanceId={instanceId}

METHOD: POST

Params：

       appName        应用名称
       instanceId     实例名称

RESPONSE：
```json
{
	
}
```
#### 5.修改实例的meta info
URL: /instance/metadata?app={appName}&instanceId={instanceId}&key=value

METHOD: POST

Params：

       appName        应用名称
       instanceId     实例名称
       key            要修改的key
       value          要修改的value

RESPONSE：
```json
{
	
}
```
## 路由管理
todo

## actuator
todo

## 在线日志
