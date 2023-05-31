<template>
	<view class="body-content">
	<view class="regist-content">
		<view class="regist-head">
			<view class="regist-head-front">
				<u--text :bold="true" size=35 text="用户信息修改"></u--text>
			</view>
		</view>
		<view class="regist-head-line">
			<u-divider  text="填写信息" textSize="25"></u-divider>
		</view>
		<!-- 注意，如果需要兼容微信小程序，最好通过setRules方法设置rules规则 -->
		<view class="regist-body">
			<u--form
					labelPosition="left"
					:model="userInfo"
					:rules="rules"
					ref="uForm"
			
			>
				<u-form-item
						label="姓名"
						prop="nickname"
						borderBottom
						ref="item1"
						label-width=100
				>
					<u--input
							v-model="userInfo.nickname"
							border="none"
					></u--input>
				</u-form-item>
				<u-form-item
						label="用户名"
						prop="username"
						borderBottom
						ref="item1"
						label-width=100
				>
					<u--input
							v-model="userInfo.username"
							border="none"
					></u--input>
				</u-form-item>
				<u-form-item
						label="电话号码"
						prop="phone"
						borderBottom
						ref="item1"
						label-width=150
				>
					<u--input
							v-model="userInfo.phone"
							border="none"
					></u--input>
				</u-form-item>
				<u-form-item
						label="用户密码"
						prop="password"
						borderBottom
						ref="item1"
						label-width=150
				>
					<u--input
							type="password"
							v-model="userInfo.password"
							border="none"
					></u--input>
				</u-form-item>
				<u-form-item
						label="确认密码"
						prop="rePassword"
						borderBottom
						ref="item1"
						label-width=150
				>
					<u--input
							type="password"
							v-model="userInfo.rePassword"
							border="none"
					></u--input>
				</u-form-item>
				<u-form-item
						label="性别"
						prop="sex"
						borderBottom
						@click="openSex"
						ref="item1"
						label-width=100
				>
					<u--input
							v-model="computeType"
							disabled
							disabledColor="#ffffff"
							placeholder="请选择性别"
							border="none"
					></u--input>
					<u-icon
							slot="right"
							name="arrow-right"
					></u-icon>
				</u-form-item>
				<u-form-item
					label="修改头像"
					borderBottom
					ref="item1"
					label-width=150
				>
					<u-upload
							:fileList="fileList1"
							@afterRead="afterRead"
							@delete="deletePic"
							name="1"
							multiple
							:maxCount="1"
					></u-upload>
				</u-form-item>
			</u--form>
			<u-action-sheet
					:show="showSex"
					:actions="actions"
					title="请选择性别"
					description="如果选择保密会报错"
					@close="showSex = false"
					@select="sexSelect"
			>
			</u-action-sheet>
		</view>
		<view class="regist-bottom">
			<u-button type="primary" @click="update" text="确定"></u-button>
		</view>
	</view>
	<view class="remaining-body">
		
	</view>
	</view>
</template>

<script>
export default {
	name: 'updateInfo',
	data() {
		return {
			showSex: false,
			fileList1: [],
			userInfo: {
				nickname: 'uView UI',
				sex: '',
				username: '',
				password: '',
				rePassword: '',
				phone: '',
			}, 
			actions: [{
				name: '男',
				value: 1
				},
				{
					name: '女',
					value: 0
				},
				{
					name: '保密',
					value: 2
				},
			],
			rules: {
				'username': {
					type: 'string',
					required: true,
					message: '请填写用户名',
					trigger: ['blur', 'change']
				},
				'nickname': {
					type: 'string',
					required: true,
					message: '请填写姓名',
					trigger: ['blur', 'change']
				},
				'password': {
					type: 'string',
					required: true,
					message: '请填写密码',
					trigger: ['blur', 'change']
				},
				'rePassword': [
					{
						type: 'string',
						required: true,
						message: '请确认密码',
						trigger: ['blur', 'change']
					},
					{
					asyncValidator: (rules, value, callback) => {
						if (value != this.userInfo.password){
							callback(new Error('两次密码校验不一致'))
						}
						  return uni.$u.test.object({values:password});
						},
						message: '两次密码校验不一致',
						trigger: ["change","blur"]
					}
				],
				'sex': {
					type: 'number',
					max: 2,
					required: true,
					message: '请选择男或女',
					trigger: ['blur', 'change']
				},
				
			},
			radio: '',
			switchVal: false
		};
	},
	onShow() {
		var self = this
		uni.getStorage({
			key:"userInfo",
			success: function (res) {
				console.log('userInfo获取成功')
				self.userInfo = res.data
				
			},
		});
	},
	methods: {
					// 删除图片
					deletePic(event) {
						this[`fileList${event.name}`].splice(event.index, 1)
					},
					// 新增图片
					async afterRead(event) {
						// 当设置 multiple 为 true 时, file 为数组格式，否则为对象格式
						let lists = [].concat(event.file)
						let fileListLen = this[`fileList${event.name}`].length
						lists.map((item) => {
							this[`fileList${event.name}`].push({
								...item,
								status: 'uploading',
								message: '上传中'
							})
						})
						for (let i = 0; i < lists.length; i++) {
							const result = await this.uploadFilePromise(lists[i].url)
							let item = this[`fileList${event.name}`][fileListLen]
							this[`fileList${event.name}`].splice(fileListLen, 1, Object.assign(item, {
								status: 'success',
								message: '',
								url: result
							}))
							fileListLen++
						}
					},
					uploadFilePromise(url) {
						var self = this
						return new Promise((resolve, reject) => {
							let a = uni.uploadFile({
								url: 'http://localhost:8081/system/avatar/save', // 仅为示例，非真实的接口地址
								filePath: url,
								name: 'file',
								formData: {
									userId: self.userInfo.id,
								},
								success: (res) => {
									setTimeout(() => {
										resolve(res.data.data)
									}, 1000)
								}
							});
						})
					},
		sexSelect(e) {
			this.userInfo.sex = e.value
			this.$refs.uForm.validateField('userInfo.sex')
		},
		update(){
			var self = this
			this.$refs.uForm.validate().then(res => {
					uni.request({
						url: self.loginServerUrl + '/system/update',
						data: self.userInfo,
						method: 'POST',
						success(res) {
							console.log("修改成功")
							uni.setStorage({
								key:"userInfo",
								data: self.userInfo,
								success() {
									console.log('loginUser储存成功');
									uni.navigateBack({
										delta: 1  // 返回的页面数，这里设为1表示返回到前一个页面
									});
								}
							});
						}
					});
				}).catch(err => {
				  console.log('表单错误信息',err);
				})
		},
		openSex(){
			uni.hideKeyboard();
			this.showSex = true;
		}
	},
	computed: {
		computerForItmType(){
			return this.userInfo.sex===0?'女':'保密';
		},
		computeType(){
		  return this.userInfo.sex===1?'男':this.computerForItmType
		}
	},
	onReady() {
		//如果需要兼容微信小程序，并且校验规则中含有方法等，只能通过setRules方法设置规则。
    	this.$refs.uForm.setRules(this.rules)
    },
};
</script>

<style>
	.body-content{
		height: 100vh;
		width: 100%;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		background-image: url("../../static/info/background1.webp");
		background-size: cover; /* 图片会被缩放以填充整个容器 */
	}
	.regist-content{
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;


		height: 100%;
	}
	.remaining-body{
		flex: 1;
	}
	.regist-head{
		margin-top: 50upx;
	}
	.regist-body{
		margin-top: 20upx;
	}
	.regist-bottom{
		margin-top: 30upx;
	}
	.regist-head-front{
		margin-top: 10upx;
	}
	.regist-head-line{
		margin-top: 10upx;
	}
</style>