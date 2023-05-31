<template>
	<view class="member-content">
		<u-index-list :index-list="keyList" custom-nav-height="100">
				<template v-for="(item, index) in memberList">
					<!-- #ifdef APP-NVUE -->
					<u-index-bar size=25  :indexList="keyList[index]"></u-index-anchor>
					<!-- #endif -->
					<u-index-item>
						<!-- #ifndef APP-NVUE -->
						<u-index-anchor size=25 :text="keyList[index]"></u-index-anchor>
						<!-- #endif -->
						<view class="list-cell"  v-for="(cell, index) in item">
							{{cell.nickName}}
						</view>
					</u-index-item>
				</template>
		</u-index-list>
	</view>

</template>

<script>
	export default {
		name: 'classMember',
		data() {
			return {
				classId: null,
				keyList: [],
				memberList: [
					
				]
			}
		},
		onShow() {
			console.log(this.classId);
			this.getMemberList();
		},
		onLoad: function(option) {
		    this.classId = option.classId;
		},
		methods: {
			getMemberList(){
				var self = this
				let classId = this.classId
				uni.request({
					url: self.serverUrl + '/class/memberList',
					data: { classId },
					method: 'GET',
					success(res) {
						console.log(res.data)
						self.keyList = res.data.data.keyList;
						self.memberList = res.data.data.memberList;
					}
				});
			}
		}

	}
</script>

<style lang="scss" scoped>
	.list-cell {
		display: flex;
		box-sizing: border-box;
		width: 100%;
		padding: 10px 24rpx;
		overflow: hidden;
		color: #323233;
		font-size: 14px;
		line-height: 35px;
		background-color: #fff;
	}
	.member-content{
		display: flex;
		margin-top: 50upx;
		flex-direction: column;
	}
</style>