<template>
	<view class="task-member-content">
		<view class="task-member-head">
			<view class="class-member-item">
				<view class="class-member-item-username">
					<u--text text="任务完成情况" size="30"></u--text>
				</view>
				<view class="class-member-item-num">
					<u-line-progress :percentage="50" height="20" inactiveColor="#ffffff" :showText="true">
						 
					</u-line-progress>
				</view>
			</view>
		</view>
		<view class="task-member-body">
				<u-divider text="已完成" textSize="25" :dashed="true"></u-divider>
				<view class="class-member-item" v-for="(item,index) in finnishList" :key="index">
					<view class="class-member-item-head-num">
					</view>
					<view class="class-member-item-head-image">
						<u-avatar  size=100 :src="item.avatarUrl"></u-avatar>
					</view>
					<view class="class-member-item-head-front">
						<view class="class-member-item-issuer">
							<u--text :bold="true" size=30 :text="item.nickName"></u--text>
						</view>

					</view>
				</view>
				<u-divider text="未完成" textSize="25" :dashed="true"></u-divider>
				<view class="class-member-item" v-for="(item,index) in unFinnishList" :key="index">
					<view class="class-member-item-head-num">

					</view>
					<view class="class-member-item-head-image">
						<u-avatar  size=100 :src="item.avatarUrl"></u-avatar>
					</view>
					<view class="class-member-item-head-front">
						<view class="class-member-item-issuer">
							<u--text :bold="true" size=30 :text="item.nickName"></u--text>
						</view>
					</view>
				</view>

		</view>
	</view>
</template>

<script>
	export default {
		name: 'taskMemberDetail',
		data() {
			return {
				classId: null,
				taskId: null,
				finnishList: [
					{nickName: '盛开的花'},{nickName: '孤独'},{nickName: '风筝'},{nickName: '航宇'},{nickName: '可爱的风'}
				],
				unFinnishList:[
					{nickName: '盛开的花'},{nickName: '孤独'},{nickName: '风筝'},{nickName: '航宇'},{nickName: '可爱的风'}
				]
			}
		},
		onLoad(option) {
			this.taskId = option.taskId;
		},
		onShow() {
			this.getMemberList();
		},
		methods:{
			getMemberList(){
				let taskId = this.taskId;
				var self = this;
				uni.request({
					url: self.serverUrl + '/task/getMember',
					data: { taskId },
					method: 'GET',
					success(res) {
						console.log(res.data)
						self.finnishList = res.data.data.finishMember;
						self.unFinnishList = res.data.data.unFinishMember;
						if(self.finnishList.length > 0){
							for (let i=0 ; i<self.unFinnishList.length ; i++) {
								self.unFinnishList[i].avatarUrl = self.avatarBaseUrl+'system/avatar/getByUserId?userId='+self.unFinnishList[i].userId
							}
						}
						if(self.unFinnishList.length > 0){
							for (let i=0 ; i<self.finnishList.length ; i++) {
								self.finnishList[i].avatarUrl = self.avatarBaseUrl+'system/avatar/getByUserId?userId='+self.finnishList[i].userId
							}
						}

						console.log(self)
					}
				});
			}
		}
	}
</script>

<style lang="scss" scoped>
	.task-member-content{
		display: flex;
		flex-direction: column;
	}
	.task-member-head{
		display: flex;
		flex-direction: column;
	}
	.task-member-body{
		display: flex;
		flex-direction: column;
		padding-top: 30upx;
		padding-left: 50upx;
		padding-right: 50upx;
	}

	.class-member-item{
		display: flex;	
		padding-top: 30upx;
		padding-left: 50upx;
		padding-right: 50upx;
	}
	.class-member-item-username{
		display: flex;	
		text-align: center;

	}
	.class-member-item-num{
		padding-top: 15upx;
	}
	.u-percentage-slot {
		padding: 1px 5px;
		background-color: $u-warning;
		color: #000000;
		border-radius: 100px;
		font-size: 14upx;
	}
	.class-member-item{
		display: flex;	
		padding-top: 30upx;
		padding-left: 50upx;
		padding-right: 50upx;
	}
	.class-member-item-head-num{
		display: flex;
		flex: 1;
		align-items: center; /* 让子项在交叉轴上顶部对齐 */
		justify-content: center; /* 让子项在主轴上左侧对齐 */
	}
	.class-member-item-head-image{
		flex: 1;
	}
	.class-member-item-head-front{
		flex: 5;
		display: flex;
		padding-left: 20upx;
		flex-direction: column;
	}
	.class-member-item-issuer{
		flex:10
	}
	.class-member-item-date{
		flex:8
	}
	.class-member-item-username{
		
	}
	.class-member-item-num{
		padding-top: 15upx;
	}
</style>