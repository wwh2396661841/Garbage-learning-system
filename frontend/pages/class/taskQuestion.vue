<template>
	<view class="task-question-content" >
		<view class="task-question-head">
			<view class="class-task-head-image">
				<u-avatar size=80 src="https://cdn.uviewui.com/uview/album/1.jpg"></u-avatar>
			</view>
			<view class="class-task-head-front">
				<view class="class-task-issuer">
					<u--text :bold="true" size=25 :text="releaseUser"></u--text>
				</view>
				<view class="class-task-date">
					<u--text type="info" size=20 :text="taskTime"></u--text>
				</view>
			</view>
		</view>
		<view class="task-question-body">
			<view class="question-detail-body" >
				<view class="question-item" v-for="(item,index) in questionList" :key="index">
					<view class="question-num">
						<u--text :text="index+1" size="30"></u--text>
					</view>
					<view class="question-name">
						<u--text :text="item.garbageName" size="30"></u--text>
					</view>
					<view class="question-type">
						<u--text :text="getType(item.garbageType)" size="30"></u--text>
					</view>					
				</view>

			</view>
		</view>
		<view class="task-question-bottom">
			<view v-if="!hasFinish()" class="task-question-todo">
				<u-button type="primary" @click="startAnswer" text="开始答题" size="mini"></u-button>
			</view>
			<view v-else="hasFinish()" class="task-question-situation">
				<u-button type="primary" text="答题情况" @click="openAnswerDetail" size="mini"></u-button>
			</view>
			<view class="task-question-member-situation">
				<u-button type="primary" text="成员答题情况" @click="openMemberDetail" size="mini"></u-button>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		name: 'taskQuestion',
		data() {
			return{
				questionList:[],
				releaseUser: null,
				taskId: null,
				classId: null,
				taskTime: null,
				finishFlag: null,
				userInfo: null,
			}
		},
		onLoad(option){
			this.taskId = option.taskId;
			this.releaseUser = option.releaseUser;
			this.taskTime = option.taskTime;
			this.classId = option.classId;
			this.finishFlag = option.finishFlag;
		},
		onShow() {
			var self = this;
			uni.getStorage({
				key:"userInfo",
				success: function (res) {
					console.log('userInfo获取成功')
					self.userInfo = res.data;
					self.getFinish();
				},
			});
			this.getClassTaskList();
		},
		methods: {

			startAnswer(){
				uni.navigateTo({
					url: `/pages/class/taskQuestionAnswer?taskId=${this.taskId}&classId=${this.classId}`
				});
			},
			openAnswerDetail(){
				uni.navigateTo({
					url: `/pages/class/taskQuestionResult?taskId=${this.taskId}`
				});
			},
			openMemberDetail(){
				uni.navigateTo({
					url: `/pages/class/taskMemberDetail?taskId=${this.taskId}`
				});
			},
			getClassTaskList(){
				let taskId = this.taskId;
				var self = this;
				uni.request({
					url: self.serverUrl + '/task/getDetail',
					data: { taskId },
					method: 'GET',
					success(res) {
						console.log(res.data)
						self.questionList = res.data.data;
					}
				});
			},
			getType(garbageType){
				let type = garbageType == 1 ? '干垃圾' : garbageType == 2 ? '湿垃圾' : garbageType == 3 ? '可回收物' : '有害垃圾';
				return type;
			},
			hasFinish(){
				let finish = this.finishFlag == '1';
				console.log(finish);
				return finish;
			},
			getFinish(){
				let taskId = this.taskId;
				let userId = this.userInfo.id;
				var self = this;
				uni.request({
					url: self.serverUrl + '/task/getFinish',
					data: { taskId,userId },
					method: 'GET',
					success(res) {
						console.log(res.data)
						self.finishFlag = res.data.data;
					}
				});
			},
		}
	}
</script>

<style>
	.task-question-content{
		display: flex;
		flex-direction: column;
	}
	.task-question-head{
		display: flex;
		padding-top: 30upx;
		padding-left: 30upx;
		padding-right: 30upx;
	}
	.task-question-body{
		display: flex;
		flex-direction: column;
		padding-top: 30upx;
		padding-left: 30upx;
		padding-right: 30upx;
	}
	.task-question-bottom{
		display: flex;
		padding-top: 30upx;
		padding-left: 30upx;
		padding-right: 30upx;
	}
	.question-detail-name{
		
	}
	.question-item{
		display: flex;
	}
	.question-detail-body{
		display: flex;
		flex-direction: column;
	}
	.question-num{
		padding-left: 30upx;
	}
	.question-name{
		padding-left: 30upx;
	}
	.question-type{
		padding-left: 30upx;
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
	.task-question-todo{
		padding-left: 50upx;
	}
	.task-question-situation{
		padding-left: 50upx;
	}
	.task-question-member-situation{
		padding-left: 50upx;
	}
</style>