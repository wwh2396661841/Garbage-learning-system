<template>
	<view class="class-advice-content">
		<view class="class-advice">
			<view class = "class-advice-head">
				
				<view class="class-advice-name">
					<u--text size=30 :bold='true' text="通知名称"></u--text>
				</view> 
				<view class="class-advice-date">
					<u--text type="info" size=24 text="通知日期"></u--text>
				</view> 
			</view>
			<view class = "class-advice-body"> 
				<u--text type="info" size=24 text="项目 'frontend' 编译成功。前端运行日志，请另行在小程序开发工具的控制台查看。"></u--text>
			</view>
			<view class = "class-advice-bottom"> 
				<view class="class-advice-button">
					<u-button shape="circle" size="mini" text="立即查看"></u-button>					
				</view>
			</view>
		</view>	
	</view>
</template>

<script>
	export default {
		name: 'classAdvice',
		data() {
	
		}
	}
</script>

<style>
	.class-advice-content{
		display: flex;
		flex-direction: column;
	}
	.class-advice{
		display: flex;
		flex-direction: column;
		margin: 30upx;
		border-radius: 2%;
		box-shadow: 0upx 0upx 12upx #e2e2e2,
		            0upx -2upx 12upx #e2e2e2,
					2upx 0upx 12upx #e2e2e2,
		            0upx 2upx 12upx #e2e2e2;
	}
	.class-advice-head{
		display: flex;
		margin-top: 20upx;
		margin-left: 30upx;
		margin-right: 30upx;
	}
	.class-advice-body{
		display: flex;
		margin-top: 20upx;
		margin-left: 30upx;
		margin-right: 30upx;
	}
	.class-advice-bottom{
		display: flex;
		margin-top: 20upx;
		margin-left: 30upx;
		margin-right: 30upx;
		margin-bottom: 20upx;
	}
	.class-advice-name{
		flex: 3;
	}
	.class-advice-date{
		flex: 1;
	}
</style>