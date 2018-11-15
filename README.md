# microservice-management
微服务治理平台
<!-- MarkdownTOC -->

- [注册管理](#%E6%B3%A8%E5%86%8C%E7%AE%A1%E7%90%86)
	- [1.注册应用列表](#1%E6%B3%A8%E5%86%8C%E5%BA%94%E7%94%A8%E5%88%97%E8%A1%A8)
	- [2.应用下实例列表](#2%E5%BA%94%E7%94%A8%E4%B8%8B%E5%AE%9E%E4%BE%8B%E5%88%97%E8%A1%A8)
	- [3.下线实例](#3%E4%B8%8B%E7%BA%BF%E5%AE%9E%E4%BE%8B)
	- [4.上线实例](#4%E4%B8%8A%E7%BA%BF%E5%AE%9E%E4%BE%8B)
	- [5.修改实例的meta info](#5%E4%BF%AE%E6%94%B9%E5%AE%9E%E4%BE%8B%E7%9A%84meta-info)
	- [6.实例集群分布,按region和zone](#6%E5%AE%9E%E4%BE%8B%E9%9B%86%E7%BE%A4%E5%88%86%E5%B8%83%E6%8C%89region%E5%92%8Czone)
	- [7.按meta匹配应用实例](#7%E6%8C%89meta%E5%8C%B9%E9%85%8D%E5%BA%94%E7%94%A8%E5%AE%9E%E4%BE%8B)
- [路由管理](#%E8%B7%AF%E7%94%B1%E7%AE%A1%E7%90%86)
	- [1.查询路由配置列表](#1%E6%9F%A5%E8%AF%A2%E8%B7%AF%E7%94%B1%E9%85%8D%E7%BD%AE%E5%88%97%E8%A1%A8)
	- [2.刷新路由](#2%E5%88%B7%E6%96%B0%E8%B7%AF%E7%94%B1)
- [actuator](#actuator)
- [在线日志](#%E5%9C%A8%E7%BA%BF%E6%97%A5%E5%BF%97)

<!-- /MarkdownTOC -->

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
#### 6.实例集群分布,按region和zone
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
#### 7.按meta匹配应用实例
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
#### 1.查询路由配置列表
URL：/routes/list

METHOD：GET

Params:

RESPONSE:
```json
{

	/servicea-prefix/ **: "servicea-service",
	/serviceb-prefix/ **: "serviceb-service"

}
```
#### 2.刷新路由

URL：/routes/refresh

METHOD:POST

Params:

RESPONSE:
```json
{
    "10.11.12.13:8500": "[]"  // key是网关实例，value是刷新结果【没有消息就是最好的消息】
}
```
## actuator
todo

## 在线日志
