<template>
	<view class="class-task-content"> 
		<view v-if="hasTask">暂无任务</view>
		<view  class="class-task" v-for="(item,index) in classTaskList">
			<view class="class-task-head">
				<view class="class-task-head-image">
					<u-avatar size=80 src="https://cdn.uviewui.com/uview/album/1.jpg"></u-avatar>
				</view>
				<view class="class-task-head-front">
					<view class="class-task-issuer">
						<u--text :bold="true" size=25 :text="item.releaseUser"></u--text>
					</view>
					<view class="class-task-date">
						<u--text type="info" size=20 :text="item.taskTime"></u--text>
					</view>
				</view>
			</view>
			<view class="class-task-body">
				<u--text size=22 text="今日垃圾分类学习任务 1.葡萄干 2.干电池.详细请点击查看"></u--text>
			</view>
			<view class="class-task-bottom">
				<view class="class-task-finish">
					<u-tag :text="item.finishNum" size="mini" shape="circle" plain ></u-tag>
				</view>
				<view class="class-task-unfinish">
					<u-tag :text="item.unFinishNum" size="mini" shape="circle"  type="info"  > </u-tag>
				</view>
				<view class="class-task-botton">
					<u-button  type="primary" @tap="openDetail(item)" size="mini" text="查看详细"></u-button>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		name: 'classTask',
		data() {
			return{
				classTaskList:[
				],
				classId: null,
				userInfo: null,
				hasTask: false,
			}
		},
		onLoad: function(option) {
		    this.classId = option.classId;

		},
		onShow: function(){
			var self = this;
			uni.getStorage({
				key:"userInfo",
				success: function (res) {
					console.log('userInfo获取成功')
					self.userInfo = res.data;
					self.getClassTaskList();
				},
			});
		},
		methods: {
			openDetail(item){
				let taskId = item.taskId;
				let releaseUser = item.releaseUser;
				let taskTime = item.taskTime;
				let finishFlag = item.finishFlag?"1":"0";
				let userInfo = this.userInfo;
				let classId = this.classId;
				console.log("12222222223256");
				uni.navigateTo({
					url: `/pages/class/taskQuestion?taskId=${taskId}&releaseUser=${releaseUser}&taskTime=${taskTime}&classId=${classId}&finishFlag=${finishFlag}`
				});
			},
			getClassTaskList(){
				console.log(this.userInfo);
				let classId = this.classId;
				let userId = this.userInfo.id;
				var self = this;
				uni.request({
					url: self.serverUrl + '/task/list',
					data: { classId,userId },
					method: 'GET',
					success(res) {
						console.log(res.data)
						self.classTaskList = res.data.data;
						console.log(self.classTaskList)
						if(!self.classTaskList){
							self.hasTask = true;
						}
					}
				});
			}
		}
	}
</script>

<style>
	.class-task-content{
		display: flex;
		flex-direction: column;
	}
	.class-task{
		display: flex;
		flex-direction: column;
		margin-top: 50upx;
		border-radius: 2%;
		box-shadow: 0upx 0upx 12upx #e2e2e2,
		            0upx -2upx 12upx #e2e2e2,
					2upx 0upx 12upx #e2e2e2,
		            0upx 2upx 12upx #e2e2e2;
	}
	.class-task-head{
		display: flex;
		margin-top: 20upx;
		margin-left: 30upx;
		margin-right: 30upx;
	}
	.class-task-head-image{
		flex: 1;
	}
	.class-task-head-front{
		flex: 5;
		display: flex;
		flex-direction: column;
	}
	.class-task-issuer{
	
	}
	.class-task-date{
		margin-top: 15upx;
	}
	.class-task-body{
		margin-top: 20upx;
		margin-left: 30upx;
		margin-right: 30upx;
	}
	.class-task-bottom{
		display: flex;
		margin-top: 20upx;
		margin-left: 30upx;
		margin-right: 30upx;
		margin-bottom: 20upx;
	}
	.class-task-finish{
	}
	.class-task-unfinish{
		margin-left: 40upx;
	}
	.class-task-botton{
		margin-left: 40upx;
	}
</style>