<template>
	<view class="item-class">
		<view class="enter-class">
			<view class="input-view">
				<u-search  placeholder="请输入班级编码或名称" height="80" :searchIconSize="40" @custom="search()" v-model="keyword"></u-search>				
			</view>
		</view>
		
		<u-divider text="推荐班级" textSize="30"></u-divider>
		<u-list>
			<u-list-item v-for="(item, index) in classList" :key="index">
				<view class="table">
					<view class="table-list" >
						<view  class="column1">
							<view class="table-image">
								<u--image shape="circle" width="180" height="180" :src="src" ></u--image>
							</view>
						</view >
						<view  class="column2">
							<view class="item1">
								<view class="class-name"><u--text :text="item.className" size="35"></u--text></view>
								<view class="class-enter-botton"><u-button text="加入" @click="open(item)" type="primary"></u-button></view>
							</view>
							<view class="item2">
								<u-tag :text="item.classUserNum" icon="man-add" size="mini" plain shape="circle"></u-tag>
							</view>
							<view class="item3">
								<u--text lines="2" size="20" :text="item.classDescribe">
								</u--text>
							</view>
						</view >
					</view>
				</view >	
			</u-list-item>
			
		</u-list>

		
		<u-popup :show="show"  overlayStyle="0.8"  mode="left" @close="close">
			<view class="class-popup">
				<view class ="class-popup-head">
					<u-divider text="班级详细" textSize="24"></u-divider>
					<view class="class-detail-head">
						<u-cell :title="classDetail.className"  :isLink="true" arrowDirection="right">
							<u-avatar
								slot="icon"
								shape="circle"
								size="120"
								src="https://cdn.uviewui.com/uview/album/1.jpg"
								customStyle="margin: -3px 5px -3px 0"
							></u-avatar>
						</u-cell>				
					</view>

				</view>
				<view class ="class-popup-body">
					<view class="class-popup-body-describe">
						<u--text :text="classDetail.classDescribe" size="25"></u--text>						
					</view>
				</view>
				<view class ="class-popup-bottom">
					<view class="bottom-button">
						<u-button text="确认" type="primary"  @click="enterClass()"></u-button>
					</view>
				</view>

			</view>
		</u-popup>

	</view>
</template>

<script>
	import uniPopup from "@/components/uni-popup/uni-popup.vue"
	import classPopup from '@/pages/class/classPopup.vue'
	export default {
		name: 'classEnter',
		components: {
			uniPopup,
			classPopup,
		},
		data() {
			return {
				classDetail: {
					className: '一起学习呀',
					classDescribe: '哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈'
				},
				classList: [
					{
						className: '一起学习呀',
						classUserNum: '12',
						classDescribe: '哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈'
					},
				],
				classId: '',
				classCode: '',
				userInfo: null,
				isFormVisible: false,
				keyword:'',
				show: false,
				src:'https://cdn.uviewui.com/uview/album/1.jpg',
				queryParam: {
					userId: null,
					pageNo: 1,
					pageSize: 5,
				}
			}
		},
		onShow: function() {
			this.getClassList();
			console.log(this.classList);
		},
		methods: {
			getClassList(){
				var self = this
				uni.getStorage({
					key:"userInfo",
					success: function (res) {
						console.log('userInfo获取成功')
						self.userInfo = res.data
						self.queryParam.userId = self.userInfo.id;
						uni.request({
							url: 'http://localhost:8899/wx/class/listNotEnter',
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
			enterClass() {
				console.log(this)
				var self = this
				uni.getStorage({
					key:"userInfo",
					success: function (res) {
						self.userInfo = res.data;
						let classId = self.classId;
						let userId = self.userInfo.id;
						let userName = self.userInfo.username
						uni.request({
							url: 'http://localhost:8899/wx/class/enter',
							data: { classId , userId , userName },
							method: 'POST',
							success(res) {
								self.close();
								self.getClassList();
							}
						});
					}
				})
			},
			createClass() {

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
			search(){
				console.log("click")
				var self = this
				let userId = this.userInfo.id;
				let code = this.keyword;
				uni.request({
					url: 'http://localhost:8899/wx/class/infoByCode',
					data: {code,userId},
					method: 'GET',
						success(res) {
						console.log("查询成功");
						self.classList = res.data.data;
						console.log(self.classList);
						}
				});
			},
			open(item) {
				console.log(item);
				this.show = true;
				this.classDetail = item;
			},
			close() {
			    this.show = false
			}
		}
	}
</script>

<style>
	.item-class {
		display: flex;
		flex-direction: column
	}

	.list-class {
		flex-direction: column
	}

	.class-list-item {}

	.enter-class {
		flex-grow: 0;
	}

	.create-class {
		display: flex;
		flex-grow: 0;
		/* border: 2px solid red; */
		padding: 10upx;
		color: #0055ff;
		font-weight: bold;
		justify-content: center;
		align-items: center;
	}


	.input-search {
		height: 80upx;
		width: 320upx;
		text-align: center;
	}

	.input-view {
		display: flex;
		justify-content: center;
		align-items: center;
		margin: 10upx 20upx 20upx 20upx;
		height: 100upx;
		border-radius: 0%;
		/* 		box-shadow: 10upx 10upx 12upx #888888; */
		/* box-shadow: 0 0 30upx #888888; */
	}

	.input-view-item {
		display: flex;
		height: 80upx;
		padding: 10upx;
		align-items: center;
		justify-content: center;
	}

	.font-search {
		/* border: 2px solid red; */
		width: 80upx;
		padding: 10upx;
		color: #72c69c;
		font-weight: bold;
	}
	.class-line{
		padding: 20upx;
	}
	.table {
	  display: flex;
	  flex-direction: column;
	  flex-grow: 1;
	  width: 100%;
	  padding-top: 20upx;
	  /* border: 1px solid #ccc; */
	  background-color: #ffffff;
	}
	.table-image{
		display: flex;
		align-items: center;
		justify-content: center;
	}
	.table-list{
		display: flex;
		width: 100%;
		height: 250upx;
	}
	.column1{
		flex: 2;
		padding: 10px;
		text-align: center;
	}
	.column2{
		display: flex;
		flex-direction: column;
		align-items: flex-start; /* 让子项垂直方向上对齐到顶部 */
		justify-content: flex-start; /* 让子项水平方向上对齐到左侧 */
		flex: 5;
		padding: 10px;
		text-align: center;
	}
	.item1{
		display: flex;
		flex: 1;
		text-align: left;
		width: 100%;
		align-items: flex-start; /* 让子项垂直方向上对齐到顶部 */
		justify-content: flex-start; /* 让子项水平方向上对齐到左侧 */
		margin-top: 10upx; /* 上外边距为20像素 */
		
	}
	.item2{
		flex: 1;
		text-align: left;
		margin-top: 10upx; /* 上外边距为20像素 */
	}
	.item3{
		flex: 1;
		width: 100%;
		text-align: left;
		margin-top: 10upx; /* 上外边距为20像素 */
		margin-bottom: 10upx; /* 下外边距为20像素 */
	}
	.class-name{
		flex: 5;
	}
	.class-enter-botton{
		flex: 2;
	}
	.class-popup{
		display: flex;
		flex-direction: column;
		width: 600upx;
	}
	.class-popup-head{
		flex: 1;
		padding-top: 20upx;
		align-items: center;
		justify-content: center; 

	}
	.class-popup-body{
		flex: 8;
		text-align: left;
	}
	.bottom-button{
		
	}
	.class-popup-bottom{
		display: flex;
		flex: 2;
		padding: 20upx;
		align-items: center;
		justify-content: center; 
	}
	.class-popup-body-describe{
		padding-top: 20upx;
		padding-left: 30upx;
		padding-right: 30upx;
	}

</style>
