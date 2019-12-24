<template>
	<section>
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px; height: 100px;width: 99%;">

		</el-col>

		<el-table :data="rows" highlight-current-row stripe border v-loading="listLoading" style="width: 99%;">
			<el-table-column prop="id" :show-overflow-tooltip="true" label="ID" min-width="30" sortable="sortable"/>
			<el-table-column prop="user.username" :show-overflow-tooltip="true" label="发布人" min-width="40" sortable="sortable" />
            <el-table-column prop="price" :show-overflow-tooltip="true" label="价格" min-width="30" sortable="sortable" />
			<el-table-column prop="type" :show-overflow-tooltip="true" label="类别" min-width="40" sortable="sortable" />
			<el-table-column prop="goodsName" :show-overflow-tooltip="true" label="标题" min-width="60" sortable="sortable" />
			<el-table-column prop="spec" :show-overflow-tooltip="true" label="描述" min-width="140" sortable="sortable" />
			<el-table-column prop="good_attachments.attachmentUrl" :show-overflow-tooltip="true" label="商品图片" min-width="50" sortable="sortable" />


			<el-table-column label="操作" width="180" align="center" fixed="right">
				<template slot-scope="scope">
					<el-button size="small" type="danger" @click="remove(scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

	</section>
</template>
<script>
	import {
		Save,
		GetAll,
		Remove
	} from '@/api/main/contract'

	let data = () => {
		return {
			formVisible: false,
			loading: false,
			form: [],
			rows: [],
			listLoading: false,
			title: ''
		}
	}

	let save = function() {
		this.title = "添加联系人";
		this.formVisible = true
		this.loading = false
		this.form = {}
		this.getRows()
	}

	let submit = function() {
		Save(this.form).catch(() => this.listLoading = false).then(res => {
			this.formVisible = false
			if(!res.data.success) {
				this.$message({
					type: 'error',
					message: res.data.message
				})
				return
			}
			this.$message({
				type: 'success',
				message: '添加成功!'
			})
			this.getRows()
		})
	}

	let getRows = function() {
		this.listLoading = true
		GetAll().then(res => {
			this.rows = res.data.content
			this.listLoading = false
		})
	}

	let edit = function(row) {
		this.formVisible = true;
		this.title = "编辑";
		this.form = Object.assign({}, row);
	}

	let remove = function(row) {
		this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
			confirmButtonText: '确定',
			cancelButtonText: '取消',
			type: 'warning'
		}).then(() => {
			if(this.listLoading)
				return
			this.listLoading = true
			Remove(row.id).catch(() => this.listLoading = false).then(res => {
				this.listLoading = false
				if(!res.data.success) {
					this.$message({
						type: 'success',
						message: '删除成功!'
					})
                    this.getRows()
					return
				}
				this.$message({
					type: 'success',
					message: '删除成功!'
				})
				this.getRows()
			})
		}).catch(() => {});
	}

	export default {
		data: data,
		methods: {
			getRows: getRows,
			save: save,
			submit: submit,
			edit: edit,
			remove: remove
		},
		mounted: function() {
			this.getRows();
		}
	}
</script>
<style>

</style>