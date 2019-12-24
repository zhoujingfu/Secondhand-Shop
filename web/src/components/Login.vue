<template>
	<el-row class="loginfrom">
		<el-col class="formTop">
			<div class="head_left">
				<!--<span>Eurasian&nbsp;Cashier</span>|-->
				<span>校园闲置交易系统管理后台</span>
			</div>
			<div class="head_right">
				<ul>
					<li>
						<a href="#">帮助支持</a>
					</li>
					<li>
						<a href="#">登录</a>
					</li>
					<li>
						<a href="#">管理中心</a>
					</li>
				</ul>
			</div>
		</el-col>

		<el-col class="form">
			<el-form :model="form" :rules="rule" ref="form" label-position="left" label-width="0px" class="demo-form login-container">
				<h3 class="title">系统登录</h3>
				<el-form-item prop="account">
					<el-input type="text" v-model="form.account" auto-complete="off" placeholder="用户名"></el-input>
				</el-form-item>
				<el-form-item prop="password">
					<el-input type="password" v-model="form.password" auto-complete="off" placeholder="密码"></el-input>
				</el-form-item>
				<el-form-item style="width:100%;">
					<el-button type="primary" style="width:100%;" @click.native.prevent="handleSubmit" :loading="logining">登录</el-button>
				</el-form-item>
				<div>
					<a href="#">忘记密码？</a>
					<a href="#">注册</a>
				</div>
			</el-form>
		</el-col>

		<el-col class="fromFooter">
			<ul class="fld_list">
				<li>关于我们</li>
				<li>开放平台协议</li>
				<li>服务平台</li>
			</ul>
			<p>Power&nbsp; By&nbsp; OYT &nbsp;pay &nbsp;Copyright &nbsp; &copy; &nbsp;2015-2017 &nbsp;All &nbsp;Right &nbsp;Reserved</p>
		</el-col>
	</el-row>
</template>

<script scoped>
	import {
		Login
	} from '@/api/login';

	let data = () => {
		return {
			logining: false,
			form: {
				account: '',
				password: ''
			},
			rule: {
				account: [{
					required: true,
					message: '请输入用户名或手机号',
					trigger: 'blur'
				}, ],
				password: [{
					required: true,
					message: '请输入密码',
					trigger: 'blur'
				}, ]
			}
		};
	}

	let submit = function() {
		this.$refs.form.validate((valid) => {
			if(!valid) {
				return false;
			}

			var params = {
				account: this.form.account,
				password: this.form.password
			};

			this.logining = true;
			Login(params).catch(e => this.logining = false).then(res => {
				this.logining = false;
				if(!res.data.success) {
					this.$message({
						message: res.data.message,
						type: 'error'
					});
					return
				}

				sessionStorage.setItem('user', JSON.stringify(res.data.value));
				this.$router.push({
					path: '/'
				});

			});
		});
	}

	export default {
		data: data,
		methods: {
			handleSubmit: submit
		}
	}
</script>

<style scoped="scoped" lang="scss">
	/*.fld_bgline{
   	 height:4em;
     line-height:4em;
   }*/

	.loginfrom a {
		text-decoration: none;
		color: #232323;
	}
	.loginfrom ul, ol {
		list-style: none;
	}
	.loginfrom img {
		border: none;
		vertical-align: middle;
	}
	.loginfrom .head_left, .loginfrom .head_right {
		padding: 0;
		margin: 0;
		display: inline-block;
	}
	.loginfrom .head_left {
		text-align: center;
		width: 61%;
		font-size: 1.5em;
	}
	.loginfrom .head_right {
		width: 35%;
	}
	.loginfrom .head_left span {
		padding: 0 .4em;
	}
	.loginfrom .head_right ul {
		overflow: hidden;
		width: 63%;
		margin: 0 auto;
		padding: 0;
	}
	.loginfrom .head_right li {
		float: left;
		width: 30%;
		text-align: center;
		border-right: 1px solid;
	}
	.loginfrom .head_right li:last-child {
		border: none;
	}
	.fld_list {
		overflow: hidden;
		width: 34%;
		margin: 3em auto 1em;
		padding: 0;
	}
	.fld_list li {
		float: left;
		width: 28%;
		text-align: center;
		border-right: 1px solid;
	}
	.fld_list li:last-child {
		border: none;
	}
	.loginfrom .fromFooter p {
		text-align: center;
	}
	.form {
		background-image: url(../assets/imgs/background.png);
		/*min-height: 460px;*/
		background-size: 100% 100%;
	}
	form {
		margin: 8% auto !important;
		width: 248px !important;
		position: absolute;
		right: 10%;
	}
	.formTop {
		height: 100%;
		padding: 1em 0;
		margin: 0;
		background: #FFFFFF;
		position: relative;
		overflow: hidden;
	}
	.fromFooter {
		height: 100%;
		background: #FFFFFF;
		margin-top: 0px;
		color: #6b6666;
	}
	.login-container {
		-webkit-border-radius: 5px;
		border-radius: 5px;
		-moz-border-radius: 5px;
		background-clip: padding-box;
		margin: 180px auto;
		width: 300px;
		padding: 35px 35px 15px 35px;
		background: #fff;
		border: 1px solid #eaeaea;
		box-shadow: 0 0 25px #cac6c6;
		.title {
			margin: 0px auto 30px;
			text-align: center;
			color: #20a0ff;
		}
	}
	@media only screen and (min-width: 300px) {
		.form {
			min-height: 460px;
		}
	}
	@media only screen and (min-width: 1800px) {
		.form {
			min-height: 680px;
		}
	}
</style>
