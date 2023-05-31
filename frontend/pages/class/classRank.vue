<template>
	<view class="class-rank-content">
		<view class="class-rank-head">
			<u-divider text="班级排行榜" textSize="30"></u-divider>
		</view>
		<view class="class-rank-body">
			
			<view class="class-rank-item" v-for="(item,index) in rankList" :key="index">
				<view class="class-rank-item-head-num">
					<view >
						<u--text :bold="true" size=30 :text="index+1"></u--text>
					</view>
				</view>
				<view class="class-rank-item-head-image">
					<u-avatar  size=100 :src="item.avatarUrl" ></u-avatar>
				</view>
				<view class="class-rank-item-head-front">
					<view class="class-rank-item-issuer">
						<u--text :bold="true" size=30 :text="item.nickName"></u--text>
					</view>
					<view class="class-rank-item-date">
						<u--text type="info" size=25 text="{{item.questionNum}}题"></u--text>
					</view>
				</view>
			</view>
	
		</view>
	</view>

</template>

<script>
	export default {
	name: 'classRank',
	data() {
		 return{
			 rankList:[
				{
					nickName: '风筝',
					questionNum: '100',
					avatarUrl: 'https://cdn.uviewui.com/uview/album/1.jpg'
				},
				{
					nickName: '孤独',
					questionNum: '90',
					avatarUrl: 'https://cdn.uviewui.com/uview/album/2.jpg'
				},
				{
					nickName: '可爱的风',
					questionNum: '80',
					avatarUrl: 'https://cdn.uviewui.com/uview/album/3.jpg'
				},
				{
					nickName: '盛开的花',
					questionNum: '70',
					avatarUrl: 'https://cdn.uviewui.com/uview/album/4.jpg'
				},
				{
					nickName: '航宇',
					questionNum: '60',
					avatarUrl: 'https://cdn.uviewui.com/uview/album/5.jpg'
				},
			],
			classId: null,
		}
	},
	onLoad(option) {
		this.classId = option.classId;
		
	},
	onShow() {
		this.getRankList();
	},
	computed: {

	},
	methods:{
		generateAvatarURL(id) {
			console.log('头像')
		    // 在这个方法中生成头像URL的逻辑
		    // 返回一个字符作为头像URL
		    return this.avatarBaseUrl+'system/avatar/getByUserId?userId='+id;
		},
		getRankList(){
			let classId = this.classId;
			var self = this;
			uni.request({
				url: self.serverUrl + '/task/getRank',
				data: { classId },
				method: 'GET',
				success(res) {
					self.rankList = res.data.data;
					for (let i=0 ; i<self.rankList.length ; i++) {
						self.rankList[i].avatarUrl = self.avatarBaseUrl+'system/avatar/getByUserId?userId='+self.rankList[i].userId
					}
					console.log(self.rankList)
				}
			});
		}
		
	}
}
</script>

<style lang="scss" scoped>
.class-rank-content{
	display: flex;
	flex-direction: column;
}
.class-rank-head{
	display: flex;
	flex-direction: column;
}
.class-rank-body{
	display: flex;
	flex-direction: column;
}
.class-rank-item{
	display: flex;	
	padding-top: 30upx;
	padding-left: 50upx;
	padding-right: 50upx;
}
.class-rank-item-head-num{
	display: flex;
	flex: 1;
	align-items: center; /* 让子项在交叉轴上顶部对齐 */
	justify-content: center; /* 让子项在主轴上左侧对齐 */
}
.class-rank-item-head-image{
	flex: 1;
}
.class-rank-item-head-front{
	flex: 5;
	display: flex;
	padding-left: 20upx;
	flex-direction: column;
}
.class-rank-item-issuer{
	flex:10
}
.class-rank-item-date{
	flex:8
}
.class-rank-item-username{
	
}
.class-rank-item-num{
	padding-top: 15upx;
}
.u-percentage-slot {
	padding: 1px 5px;
	background-color: $u-warning;
	color: #000000;
	border-radius: 100px;
	font-size: 14upx;
}
</style>