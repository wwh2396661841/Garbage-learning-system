<template>
	<view class="user-info-content"> 
		<view class="user-info">
			<view class="user-info-head">
				<view class="user-info-head-image">
					<u-avatar shape="square" size=100 
					:src="getAvatarSrc"></u-avatar>
				</view>
				<view class="user-info-head-front">
					<view class="user-info-issuer">
						<u--text :bold="true" size=30 :text="userInfo.nickname?userInfo.nickname:'未登录'"></u--text>
					</view>
					<view class="user-info-date">
						<u--text type="info" size=25 :text="userInfo.id?userInfo.id:''"></u--text>
					</view>
				</view>
			</view>
			<!-- <view class="user-info-body"> -->
				<!-- <u--text size=22 text="项目 'frontend' 编译成功。前端运行日志/n，请另行在小程序开发工具的控制台查看。项目 'frontend' 编译成功。前端运行日志，请另行在小程序开发工具的控制台查看。项目 'frontend' 编译成功。前端运行日志，请另行在小程序开发工具的控制台查看。"></u--text> -->
			<!-- </view> -->
			<view class="user-info-bottom">
				<!-- <view class="user-info-finish">
					<u-tag text="标签" size="mini" shape="circle" plain > </u-tag>
				</view>
				<view class="user-info-unfinish">
					<u-tag text="标签" size="mini" shape="circle"  type="info"  > </u-tag>
				</view> -->
			</view>
		</view>
		
		<view class="user-info-item">
			<u-cell-group>
				<u-cell icon="share" v-if="login" title="修改信息" :isLink="true" arrow-direction="right" @click="openUpdateInfo"></u-cell>
				<u-cell icon="map" v-if="login" title="退出登录" :isLink="true" arrow-direction="right" @click="logout"></u-cell>
				<u-cell icon="map" v-if="login" title="修改头像" :isLink="true" arrow-direction="right" @click="openAvatar"></u-cell>
				<u-cell icon="map" v-if="!login" title="注册" :isLink="true" arrow-direction="right" @click="openRegist"></u-cell>
				<u-cell icon="map" v-if="!login" title="登陆" :isLink="true" arrow-direction="right" @click="open"></u-cell>
			</u-cell-group>
		</view>
		<u-popup :show="popupShow"  @close="close" @open="open">
			<view class="confirm_search">
			  <u-form :model="loginUser" label-width="120" :border-bottom="false"  label-position="left">
				<view class="form-head">
					<view class="form-head-icon">
						
					</view>
					<view class="form-head-front">
						<u--text :bold="true" size="40" text="登录" ></u--text>				
					</view>
			
				</view>
			
				<view class="form-body">
					<u-line length="100%"  margin="10upx"></u-line>
					<u-form-item label="用户名:" prop="loginUser.username" labelPosition ="left" :border-bottom="false">
						<u-input v-model="loginUser.username" />
					</u-form-item>
					<u-line length="100%"  margin="10upx"></u-line>
					<u-form-item label="密 码:" prop="loginUser.password" labelPosition ="left" :border-bottom="false">
						<u-input type="password" v-model="loginUser.password" />
					</u-form-item>
					<u-line length="100%"  margin="10upx"></u-line>
				</view>
			
				<view class="from-bottom">
					<u-button type="primary" @click="loginForm()" text="确定"></u-button>
				</view>
			  </u-form>		
			</view>
		</u-popup>
		<u-notify  ref="uNotify"></u-notify>
	</view>
	
</template>

<script>
	export default {
		name:'info',
		data(){
			return{
				popupShow: false,
				loginUser: {
					username:'',
					password:''
				},
				userInfo: null,
				login: false,
			}
		},

		onShow() {
			var self = this
			uni.getStorage({
				key:"userInfo",
				success: function (res) {
					console.log('userInfo获取成功')
					self.userInfo = res.data
					self.login = true;
					if(self.userInfo){
						self.popupShow = false;
					}
					else{
						self.popupShow = true;				
					}
				},
			});

		},
		computed: {
		    getAvatarSrc() {
		      // 在这个方法中编写逻辑，根据需要返回字符作为u-avatar的src属性的值
		      // 例如，可以根据当前用户的信息动态生成头像URL
		      // 返回一个字符作为u-avatar的src属性
		      return this.generateAvatarURL();
		    }
		},
		methods:{
			generateAvatarURL() {
			    // 在这个方法中生成头像URL的逻辑
			    // 返回一个字符作为头像URL
				if(this.userInfo){
					return this.avatarBaseUrl+'system/avatar/getByUserId?userId='+this.userInfo.id;	
				}else{
					return '';
				}
			    
			},
			showMessage(message){
				this.$refs.uNotify.show({
				    
				    type: 'primary',
				    color: '#55ffff',
				    bgColor: '#55aa00',
				    message: message,
					fontSize: 30,

				    duration: 1000 * 3,
				    
				})
			},
			loginForm(){
				var self = this
				var token =''
				uni.request({
					url: self.loginServerUrl + '/system/login',
					data: self.loginUser,
					method: 'POST',
					success(res) {
						console.log(res.data);
						self.userInfo=res.data.data.systemUser;
						token = res.data.data.token;
						console.log("userInfo")
						console.log(self.userInfo)
						uni.setStorage({
							key:"userInfo",
							data: self.userInfo,
							success() {
								console.log('loginUser储存成功');
							}
						});
						self.popupShow = false;
						uni.setStorage({
							key:"token",
							data: token,
							success() {
								console.log('loginUser储存成功');
							}
						});
						self.login = true;
						self.showMessage("登陆成功");
					}
				})

				// uni.getStorage({
				// 	key:"loginUser",
				// 	success: function (res) {
				// 		console.log('loginUser获取成功')
				// 		self.loginInfo = res.data
						
				// 	}
			},
			logout(){
				var token ='';
				var self = this;
				uni.getStorage({
					key:"token",
					success: function (res) {
						console.log('token获取成功')
						token = res.data
						
					},
				});
				uni.request({
					url: self.loginServerUrl + '/system/logout',
					data: token,
					method: 'POST',
					success(res){
						console.log('退出成功');
						uni.removeStorage({
							key:"token",
							success: function (res) {
								console.log('token移除成功')
							},
						});
						uni.removeStorage({
							key:"userInfo",
							success: function (res) {
								console.log('userInfo移除成功')
							},
						});
						self.userInfo = null;
						self.login = false;
						self.showMessage("成功退出")
					}

				});
			},
			open(){
				this.popupShow = true;
			},
			close(){
				this.popupShow = false;
			},
			openUpdateInfo(){
				if(this.userInfo){
					uni.navigateTo({
						url: `/pages/info/updateInfo`
					});
				}
				else{
					this.popupShow = true;
				}
			},
			openRegist(){
				uni.navigateTo({
					url: `/pages/info/regist`
				});
			},
			openAvatar(){
				uni.navigateTo({
					url: `/pages/info/avatar`
				});
			},
			openPopup(){
				this.popupShow = true;
			},
		}
	}
</script>

<style>
	.user-info-content{
		display: flex;
		flex-direction: column;
		background-color: #e8e8e8;
		height: 100%;
	}
	.user-info{
		display: flex;
		flex-direction: column;
		padding-top: 50upx;
		border-radius: 2%;
		box-shadow: 0upx 0upx 0upx #e2e2e2,
		            0upx 0upx 0upx #e2e2e2,
					0upx 0upx 0upx #e2e2e2,
		            0upx 2upx 0upx #e2e2e2;
		background-color: #ffffff;	
	}
	
	.user-info-item{
		display: flex;
		flex-direction: column;
		margin-top: 50upx;
		border-radius: 2%;
		box-shadow: 0upx 0upx 0upx #e2e2e2,
		            0upx -2upx 0upx #e2e2e2,
					0upx 0upx 0upx #e2e2e2,
		            0upx 2upx 0upx #e2e2e2;
		background-color: #ffffff;
	}
	.user-info-head{
		display: flex;
		margin-top: 20upx;
		margin-left: 30upx;
		margin-right: 30upx;
	}
	.user-info-head-image{
		flex: 1;
	}
	.user-info-head-front{
		flex: 5;
		display: flex;
		flex-direction: column;
	}
	.user-info-issuer{
		flex:1
	}
	.user-info-date{
		flex:1
	}
	.user-info-body{
		margin-top: 20upx;
		margin-left: 30upx;
		margin-right: 30upx;
	}
	.user-info-bottom{
		display: flex;
		margin-top: 20upx;
		margin-left: 30upx;
		margin-right: 30upx;
		margin-bottom: 20upx;
	}
	.user-info-finish{
	}
	.user-info-unfinish{
		margin-left: 40upx;
	}

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
