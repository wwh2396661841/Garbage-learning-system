<template>
	<view class="content">
		<view class="nav">
			<view class="nav-left">
				<scroll-view scroll-y>
					<view @tap="selectClassify(item.index)" class="nav-left-item" v-for="(item,index) in garbageSort" :class="item.active?item.class:item.classborder"
					 :key='index'>
						{{item.name}}
					</view>

				</scroll-view>
			</view>
			<view class="nav-right">
				<!-- <view >
					<ad unit-id="adunit-060249bea9401e5c"></ad>
				</view> -->
				<scroll-view scroll-y :style="'height:'+height+'px'" scroll-with-animation>
					<view class="view-img">
						<view>
							<image v-if="index!=null" class="show-img" :src="'../../static/showimg/style1-'+index+'.jpg'"></image>
						</view>
					</view>
					<view @tap="changeColor(item)" v-bind:id="item.questionId" 
					v-for="(item,i) in currentDetail" 
					:key="i" 
			
					 v-bind:class="{ 'clicked': clickedItems.includes(item) ,
									'nav-right-item' : true ,
									'style1' : i%2==0}">
						<view>{{item.garbageName}}</view>
					</view>
					<view class="nav-right-bottom" v-if="index == 5">
						<u-button  @click="release" type="primary" text="确定"></u-button>
					</view>
				</scroll-view>

			</view>
		</view>
		<u-notify  ref="message"></u-notify>
	</view>
</template>

<script>

	export default {
		name: 'classTaskRelease',
		data() {
			return {
				height: 0,
				index: null,
				classId: null,
				detailPopupShow: false,
				detailShowObject: {},
				clickedItems:[],
				// detailPopupShow: true,
				// detailShowObject: {
				// 	keyword: "纸巾",
				// 	garbageType: 1
				// },
				garbageSort: [{
						index: 1,
						name: "干垃圾",
						class: 'garbage-gan',
						classborder: 'garbage-gan-border',
						active: true,
					},
					{
						index: 2,
						name: "湿垃圾",
						class: 'garbage-shi',
						classborder: 'garbage-shi-border',
						active: false,
					},
					{
						index: 3,
						name: "可回收物",
						class: 'garbage-huishou',
						classborder: 'garbage-huishou-border',
						active: false,
					},
					{
						index: 4,
						name: "有害垃圾",
						class: 'garbage-youhai',
						classborder: 'garbage-youhai-border',
						active: false,
					},
					{
						index: 5,
						name: "发布任务",
						class: 'garbage-release',
						classborder: 'garbage-release-border',
						active: false,
					}
				],
				hadVisit: [],
				garbageSortDetail: [
					[],
					[],
					[],
					[],
					[]
				],
				currentDetail: [],

			}
		},
		// watch:{
		// 	'getApp().globalData.typeid':function (val,oldVal) {
		// 		console.log('new: %s, old: %s', val, oldVal)
		// 	}
		// },
		// onShareAppMessage() {
		// 	return {
		// 		title: "这是分类页的分享",
		// 		path: '/pages/index/index',
		// 		imageUrl: this.image ? this.image : 'https://img-cdn-qiniu.dcloud.net.cn/uniapp/app/share-logo@3.png'
		// 	}
		// },
		onLoad(option) {
			this.classId = option.classId;
			uni.login({
				provider: 'weixin',
				success: function(loginRes) {
					console.log(loginRes)
					console.log(loginRes.authResult);
					// 获取用户信息
					uni.getUserInfo({
						provider: 'weixin',
						success: function(infoRes) {
							console.log(infoRes)
							console.log('用户昵称为：' + infoRes.userInfo.nickName);
						}
					});
				}
			});
			console.log("onLoad")
			this.height = uni.getSystemInfoSync().windowHeight;
			console.log(uni.getSystemInfoSync().windowHeight)
			console.log(uni.getSystemInfoSync().screenHeight)
			console.log("typeid:" + getApp().globalData.typeid)
		},
		onShow() {
			console.log("onshow ")
			this.selectClassify(getApp().globalData.typeid);
			console.log(getApp().globalData.typeid)
		},
		methods: {
			showMessage(message){
				this.$refs.message.show({
				    
				    type: 'primary',
				    color: '#55ffff',
				    bgColor: '#55aa00',
				    message: message,
					fontSize: 30,
			
				    duration: 1000 * 3,
				    
				})
			},
			release() {
				var self = this
				if(self.clickedItems.length = 10){
					let classId = self.classId;
					let questions = self.clickedItems;
					console.log(questions)
					uni.request({
						url: self.serverUrl + '/task/save',
						data: { classId , questions },
						method: 'POST',
						success(res) {
							self.showMessage("发布成功");
							setTimeout(function() {
							  // 在这里写下你想要延迟执行的代码
							  uni.navigateBack({
							  	delta: 1  // 返回的页面数，这里设为1表示返回到前一个页面
							  });
							}, 1000); // 1000毫秒等于1秒
						}
					});
				}
				else{
					
				}
			},
			insertAd() {
				// 在页面中定义插屏广告
				let interstitialAd = null
			
				// 在页面onLoad回调事件中创建插屏广告实例
				if (wx.createInterstitialAd) {
					interstitialAd = wx.createInterstitialAd({
						adUnitId: 'adunit-c9a12748b3ed91fa'
					})
					interstitialAd.onLoad(() => {})
					interstitialAd.onError((err) => {})
					interstitialAd.onClose(() => {})
				}
			
				// 在适合的场景显示插屏广告
				if (interstitialAd) {
					interstitialAd.show().catch((err) => {
						console.error(err)
					})
				}
			},
			changeColor(item){
				console.log(item);
				var itemIndex = this.clickedItems.indexOf(item);
				if (itemIndex === -1) {
					this.clickedItems.push(item);
				} else {
				    this.clickedItems.splice(itemIndex, 1);
				}
			},
			showDetailGarbage(item) {
				this.detailShowObject = {
					garbageType: item.garbageType,
					keyword: item.garbageName,
					remark: item.remark
				};
				this.detailPopupShow = true;
			},
			hideMypopup() {
				this.detailPopupShow = false;
			},
			selectClassify(index) {
				let me = this;
				if (me.index == index){
					return false
				} 
				else if(index == 5){
					me.currentDetail = me.clickedItems
					me.index = index;
					getApp().globalData.typeid = index;
					me.garbageSort.forEach(item => {
						if (item.index != index) {
							item.active = false;
						} else {
							item.active = true;
						}
					
					});
					return false;
				};
				getApp().globalData.typeid = index
				me.index = index;
				// console.log("choose index"+index);
				me.garbageSort.forEach(item => {
					if (item.index != index) {
						item.active = false;
					} else {
						item.active = true;
					}

				});
				console.log(me.hadVisit.indexOf(me.index))
				if (me.hadVisit.indexOf(me.index) != -1) {
					console.log("加载缓存中的数据")
					me.currentDetail = me.garbageSortDetail[index];
					return;
				}
				me.hadVisit.push(me.index);

				//  根据 id 去后台获取接口信息； 判断一下id 是否有变化；无变化则不改变
				uni.request({
					url: me.serverUrl + `/qb/type/${index}`, //仅为示例，并非真实接口地址。
					success: (res) => {
						console.log(res)
						// console.log(res.data.data);
						me.garbageSortDetail[index] = res.data.data;
						me.currentDetail = me.garbageSortDetail[index];
						// console.log(me.garbageSortDetail[1])
					}
				});
			}
		}
	}
</script>

<style>
	@import url("type.css");
</style>
