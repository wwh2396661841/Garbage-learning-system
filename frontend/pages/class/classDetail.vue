<template>
	<view class="class-detail">
		<view class="class-detail-head">
			<view class="class-detail-head-name">
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
			<view class="class-detail-head-setting">
				<u-grid col="4">
					<u-grid-item
					    v-for="(baseListItem,baseListIndex) in baseList"
					    :key="baseListIndex"
						@click="openPage(baseListItem.title)"
						v-if="baseListItem.show"
					>
						<u-icon
							:customStyle="{paddingTop:20+'rpx'}"
							:name="baseListItem.name"
							:size="60"
						></u-icon>
						<text class="grid-text">{{baseListItem.title}}</text>
					</u-grid-item>
				</u-grid>
			</view>
			
		</view>
		<view class="class-detail-body">
			
		</view>
		<view class="class-detail-bottom">
			
		</view>
	</view>
</template>

<script>
	export default{
		name: 'classDetail',
		data(){
			return{
				classId: null,
				classDetail: null,
				userInfo: null,
				deleteParam: {
					classId: null,
					userId: null
				},
				userType: null,
				baseList:[
					{
						name: 'photo',
						title: '任务',
						permissions: '0',
						show: null,
                    },
      //               {
      //                   name: 'lock',
      //                   title: '通知',
						// permissions: '0',
						// show: null,
      //               },
                    {
                        name: 'star',
                        title: '排行榜',
						permissions: '0',
						show: null,
                    },
					{
					    name: 'star',
					    title: '成员',
						permissions: '0',
						show: null,
					},
					{
					    name: 'star',
					    title: '发布任务',
						permissions: '1',
						show: null,
					},
					{
					    name: 'star',
					    title: '修改信息',
						permissions: '1',
						show: null,
					},
					{
					    name: 'star',
					    title: '退出',
						permissions: '0',
						show: null,
					},
					],
			}
		},
		onLoad: function(option) {
		    this.classId = option.classId;
		},
		onShow() {
			var self = this;

			uni.getStorage({
				key:"userInfo",
				success: function (res) {
					console.log('userInfo获取成功')
					self.userInfo = res.data
					let classId = self.classId;
					let userId = self.userInfo.id
					uni.request({
						url: self.serverUrl + '/class/infoById',
						data: { classId,userId },
						method: 'GET',
						success(res) {
							console.log("查询成功");
							self.classDetail = res.data.data.classInfo;
							self.userType = res.data.data.classUser.userType;
							console.log(self.userType);
							self.hasPermissions();
							console.log(self.baseList);
						}
					});
				},
			});
		},
		methods: {
			hasPermissions(){
				for (var i = 0; i < this.baseList.length; i++) {
					if(this.baseList[i].permissions == '0'){
						this.baseList[i].show = true;
					}
					else{
						console.log(this.userType == '1')
						if(this.userType == '1'){
							this.baseList[i].show = true;
						}
						else{
							this.baseList[i].show = false;
						}
					}
				}
			},
			openPage(title){
				let id = this.classId;
				if('任务' === title){
					uni.navigateTo({
						url: `/pages/class/classTask?classId=${id}`
					});
				}
				else if('发布任务' === title){
					uni.navigateTo({
						url: `/pages/class/classTaskRelease?classId=${id}`
					});
				}
				else if('通知' === title){
					uni.navigateTo({
						url: '/pages/class/classAdvice'
					});
				}
				else if('修改信息' === title){
					uni.navigateTo({
						url: `/pages/class/classForm?type=update&classId=${id}`
					});
				}
				else if('排行榜' === title){
					uni.navigateTo({
						url: `/pages/class/classRank?classId=${id}`
					});
				}
				else if('成员' === title){
					uni.navigateTo({
						url: `/pages/class/classMember?classId=${id}`
					});
				}
				else if('退出' === title){
					var self = this
					self.deleteParam.userId = self.userInfo.id;
					self.deleteParam.classId =self.classId;
					uni.request({
						url: 'http://localhost:8899/wx/class/quit',
						data: self.deleteParam,
						method: 'GET',
						success(res) {
						console.log("查询成功");
						self.classList = res.data.data;
							console.log(self.classList);
							uni.navigateBack({
								delta: 1  // 返回的页面数，这里设为1表示返回到前一个页面
							});
						}
					});
				}
			}
		}
	}
</script>

<style>
	.class-detail{
		display: flex;
		flex-direction: column;
	}
	.class-detail-head{
		display: flex;
		flex-direction: column;
		padding-top: 20upx;
	}
	.class-detail-head-name{

	}
	.class-detail-head-setting{
		
	}
</style>