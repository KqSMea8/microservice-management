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
#### 实例集群分布,按region和zone
URL： cluster?app={appName}
METHOD: GET
Params：
    appName        应用名称
RESPONSE:
```json
{
	default: {    //region
		default: [{   // zone=default，没有设置默认default
				instanceId: "172.22.187.91:8882",
				status: "UP",
				metadata: {
					weight: "10",
					label: "testing"
				}
			},
			{
				instanceId: "172.22.181.42:8882",
				status: "UP",
				metadata: {
					weight: "10",
					label: "testing"
				}
			},
			{
				instanceId: "172.22.187.95:8882",
				status: "DOWN",
				metadata: {
					weight: "10",
					label: "testing"
				}
			},
			{
				instanceId: "192.168.1.6:8882",
				status: "DOWN",
				metadata: {
					weight: "10",
					label: "testing"
				}
			}
		],
		tc: [{   // zone=tc
				instanceId: "192.168.1.8:8883",
				status: "UP",
				metadata: {
					zone: "tc",
					weight: "10",
					label: "testing"
				}
			},
			{
				instanceId: "192.168.1.9:8882",
				status: "UP",
				metadata: {
					zone: "tc",
					weight: "10",
					label: "testing"
				}
			}
		]
	}
}
```
#### 按meta匹配应用实例
URL： group?app={appName}&condition={condition}&value=&{value}
METHOD: GET
Params：
    appName        应用名称
    condition      匹配条件key，目前只支持meta中的key
    value          匹配条件value，如果为空，返回为按condition分组，非空的话进行严格匹配
RESPONSE:   
```json
{
	tc: [{   //按 condition=zone，value=tc匹配
			instanceId: "192.168.1.8:8883",
			status: "UP",
			metadata: {
				zone: "tc",
				weight: "10",
				label: "testing"
			}
		},
		{
			instanceId: "192.168.1.9:8882",
			status: "UP",
			metadata: {
				zone: "tc",
				weight: "10",
				label: "testing"
			}
		}
	]
}
```
## 路由管理
todo

## actuator
todo

## 在线日志
