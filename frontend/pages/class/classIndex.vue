<template>
	<view class="class-index">
		<view class="class-head">
			<u-cell-group >
				<view class="enter-cell" >
					<u-cell icon="arrow-right" title="加入班级" :arrow="true" @click="enterClass()"  arrow-direction="right"></u-cell>
					<u-cell class="create-cell" icon="arrow-right" title="创建班级" :arrow="true" @click="createClass()" arrow-direction="right"></u-cell>
				</view>

			</u-cell-group>		
		</view>
		<view class="class-body">
			<u-divider text="我的班级" textSize="30"></u-divider>
			<u-list>
				<u-list-item v-for="(item,index) in classList" :key="index">
					<view class="class-item">
						<u-cell icon="arrow-right" :title="item.className" @click="openDetail(item.id)">
					
						</u-cell>						
					</view>
				</u-list-item>
			</u-list>
		</view>		
	</view>

</template>

<script>
	export default {
		name: 'classIndex',
		data() {
			return {
				userInfo: null,
				classList: [],
				queryParam: {
					userId: null,
					pageNo: 1,
					pageSize: 5,
				}
			}
		},
		onShow: function() {
			this.userInfo = null;
			this.classList = null;
			this.getClassList();
		},
		methods: {
			enterClass(code) {
				uni.navigateTo({
					url: `/pages/class/classEnter`
				});
			},
			createClass() {
				uni.navigateTo({
					url: `/pages/class/classForm`
				});
			},
			getClassList(){
				var self = this
				uni.getStorage({
					key:"userInfo",
					success: function (res) {
						console.log('userInfo获取成功')
						self.userInfo = res.data
						self.queryParam.userId = self.userInfo.id;
						uni.request({
							url: self.serverUrl + '/class/list',
							data: self.queryParam,
							method: 'GET',
							success(res) {
								console.log("查询成功");
								self.classList = res.data.data;
								console.log(self.classList);
							}
						});
					},
				});
			},
			showForm() {
				this.isFormVisible = true;
			},
			hideForm() {
				this.isFormVisible = false
			},
			handleSubmit(formData) {
				// do something with the form data
				this.hideForm()
			},
			openDetail(classId){
				let id = classId;
				uni.navigateTo({
					url: `/pages/class/classDetail?classId=${id}`
				});
			}
		}
	}
</script>

<style>
	.class-index{
		display: flex;
		flex-direction: column;
	}
	.class-head{
		flex-grow: 0;
		padding-top: 30upx;
	}
	.class-body{
		flex-grow: 1;
		padding-top: 15upx;
	}
	.enter-cell{
		background-color: rgba(170, 255, 255, 0.3);
	}
	.class-item{
		background-color: rgba(170, 170, 255, 0.3);
	}
</style>