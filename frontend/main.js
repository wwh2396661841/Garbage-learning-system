import Vue from 'vue'
import App from './App'
import uView from '@/uni_modules/uview-ui'

Vue.config.productionTip = false

// prod 
// Vue.prototype.serverUrl="https://felix.picp.vip/wx"
// dev
 Vue.prototype.serverUrl="http://localhost:8899/wx"
 Vue.prototype.loginServerUrl="http://localhost:8081/"
 Vue.prototype.avatarBaseUrl="http://localhost:8081/"
 
 Vue.use(uView)
uni.$u.setConfig({
	// 修改$u.config对象的属性
	config: {
		// 修改默认单位为rpx，相当于执行 uni.$u.config.unit = 'rpx'
		unit: 'rpx'
	},
	// 修改$u.props对象的属性
	props: {
		// 修改radio组件的size参数的默认值，相当于执行 uni.$u.props.radio.size = 30
		radio: {
			size: 15
		}
		// 其他组件属性配置
		// ......
	}
})


App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()
