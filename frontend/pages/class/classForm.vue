<template>
	<view class="confirm_search">
	  <u-form :model="form" label-width="160" :border-bottom="false"  label-position="left">
		<view class="form-head">
			<view class="form-head-icon">
				
			</view>
			<view class="form-head-front">
				<u--text :bold="true" size="40" text="创建班级" ></u--text>				
			</view>

		</view>

		<view class="form-body">
			<u-line length="100%"  margin="10upx"></u-line>
			<u-form-item label="班级名称:" prop="className" labelPosition ="left" :border-bottom="false">
				<u-input v-model="form.className" />
			</u-form-item>
			<u-line length="100%"  margin="10upx"></u-line>
			<u-form-item label="班级编码:" prop="classCode" labelPosition ="left" :border-bottom="false">
				<u-input v-model="form.classCode" />
			</u-form-item>
			<u-line length="100%"  margin="10upx"></u-line>
			<u-form-item label="班级描述:" prop="describe" labelPosition ="left" :border-bottom="false">
				<u--textarea v-model="form.classDescribe"  ></u--textarea>
			</u-form-item>	
			<u-line length="100%"  margin="10upx"></u-line>
		</view>

		<view class="from-bottom">
			<u-button type="primary" text="确定" @click="action"></u-button>
		</view>
	  </u-form>	
		<u-notify  ref="message"></u-notify>
	</view>

</template>

<script>
export default {
  name: 'Form',
  data() {
    return {
		userInfo: null,
		classId: null,
		form: {
			classCode: '',
			className: '',
			classDescribe:'',
			userId: null,
			nickName: null,
			userName: null,
		},
		type: null,
    }
  },
  onShow() {
  	if(this.type == 'update'){
		this.getDetail();
	}
  },
  onLoad(option) {
	console.log(option)
	this.type = option.type
	this.classId = option.classId
  },
  methods: {
	showMessage(message){
	  	this.$refs.message.show({
	  	    
	  	    type: 'primary',
	  	    color: '#55ffff',
	  	    bgColor: '#55aa00',
	  	    message: message,
	  		fontSize: 30,
	  
	  	    duration: 1000 * 3,
	  	    
	  	})
	},
	getDetail(){
		console.log('班级信息')
		var self = this;
		uni.getStorage({
			key:"userInfo",
			success: function (res) {
				console.log('userInfo获取成功')
				self.userInfo = res.data
				console.log(self.userInfo)
				let form = {
					userId: self.userInfo.id,
					classId: self.classId
				};
				uni.request({
					url: self.serverUrl + '/class/infoById',
					data: form,
					method: 'GET',
					success(res) {
						console.log(res.data.data)
						self.form.classCode = res.data.data.classInfo.classCode;
						self.form.classDescribe = res.data.data.classInfo.classDescribe;
						self.form.className = res.data.data.classInfo.className;
						self.form.id = res.data.data.classInfo.id;
						self.form.userId = res.data.data.classUser.id;
						self.form.nickName = res.data.data.classUser.nickName;
						self.form.userName = res.data.data.classUser.userName;
						console.log("创建成功")
					}
				});
			},
		});

	},
	action(){
		if(this.type == 'update'){
			this.updateClass();
		}else{
			this.createClass();
		}
	},
	createClass(){
		var self = this 
		uni.getStorage({
			key:"userInfo",
			success: function (res) {
				console.log('userInfo获取成功')
				self.userInfo = res.data
				console.log(self.userInfo)
				self.form.userId = self.userInfo.id;
				self.form.nickName = self.userInfo.nickname;
				self.form.userName = self.userInfo.username;
				uni.request({
					url: self.serverUrl + '/class/save',
					data: self.form,
					method: 'POST',
					success(res) {
						self.showMessage("创建成功")
						setTimeout(function() {
						  // 在这里写下你想要延迟执行的代码
						  uni.navigateBack({
						  	delta: 1  // 返回的页面数，这里设为1表示返回到前一个页面
						  });
						}, 500); // 1000毫秒等于1秒
					}
				});
			},
		});

	},
	updateClass(){
		var self = this 
		uni.getStorage({
			key:"userInfo",
			success: function (res) {
				console.log('userInfo获取成功')
				self.userInfo = res.data
				console.log(self.userInfo)
				self.form.userId = self.userInfo.id;
				self.form.nickName = self.userInfo.nickname;
				self.form.userName = self.userInfo.username;
				uni.request({
					url: self.serverUrl + '/class/update',
					data: self.form,
					method: 'POST',
					success(res) {
						self.showMessage("修改成功")
						setTimeout(function() {
						  // 在这里写下你想要延迟执行的代码
						  uni.navigateBack({
						  	delta: 1  // 返回的页面数，这里设为1表示返回到前一个页面
						  });
						}, 1000); // 1000毫秒等于1秒
					}
				});
			},
		});
	
	},
	onBackPress() {
	    var pages = getCurrentPages();
	    var Page = pages[pages.length - 1];//当前页
	    var prevPage = pages[pages.length - 2]; //上一个页面
	    prevPage.$vm.init =true; // coupon是上个页面里定义的变量
	    setTimeout(() => {
	        uni.navigateBack({ delta: 1 });    // 返回上一页
	    }, 1000)
	},
  }
}
</script>

<style scoped>
	.confirm_search{
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}
	.form-head{
		display: flex;
		justify-content: center;
		align-items: center;
		padding-top: 20upx;
	}
	.form-body{
		padding: 20upx;
	}
	.form-bottom{
		padding: 20upx;
	}
</style>