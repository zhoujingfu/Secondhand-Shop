<template>
    <section>
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px; height: 100px;width: 99%;">

        </el-col>

        <el-table :data="rows" highlight-current-row stripe border v-loading="listLoading" style="width: 99%;">
            <el-table-column prop="id" :show-overflow-tooltip="true" label="用户ID" min-width="30" sortable="sortable" />
            <el-table-column prop="username" :show-overflow-tooltip="true" label="用户名" min-width="30" sortable="sortable" />
            <el-table-column prop="nickName" :show-overflow-tooltip="true" label="微信名" min-width="50" sortable="sortable" />
            <el-table-column prop="mobile" :show-overflow-tooltip="true" label="电话" min-width="40" sortable="sortable" />
            <el-table-column prop="qq" :show-overflow-tooltip="true" label="学号" min-width="130" sortable="sortable" />
            <el-table-column prop="qq" :show-overflow-tooltip="true" label="身份凭证" min-width="50" sortable="sortable" />

            <el-table-column label="状态" width="80" align="center" fixed="right">
                <template slot-scope="scope">
                    <el-button size="small" type="success" v-if='scope.row.state===0'rows.state="1">正常</el-button>
                    <el-button size="small" type="danger"   v-if='scope.row.state===1' rows.state="0">锁定</el-button>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="180" align="center" fixed="right" >
                <template slot-scope="scope">
                    <el-button size="small" type="danger" @click="remove(scope.row) " v-if='scope.row.state===0'rows.state="1">锁定用户</el-button>
                    <el-button size="small" type="success" @click="save(scope.row) " v-if='scope.row.state===1' rows.state="0">解锁用户</el-button>
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
    } from '@/api/main/adminuser'

    let data = () => {
        return {
            formVisible: false,
            loading: false,
            form: {},
            rows: [],
            listLoading: false,
            title: ''
        }
    }


    let getRows = function() {
        this.listLoading = true
        GetAll().then(res => {
            this.rows = res.data
            this.listLoading = false
        })
    }

    let edit = function(row) {
        this.formVisible = true;
        this.title = "编辑";
        this.form = Object.assign({}, row);
    }

    let remove = function(row) {
        this.$confirm('是否更改用户状态', '提示', {
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
                        message: '锁定成功!'
                    })
                    this.getRows()
                }


            })
        }).catch(() => {});
    }
    let save = function(row) {
        this.$confirm('是否更改用户状态', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            if(this.listLoading)
                return
            this.listLoading = true
            Save(row.id).catch(() => this.listLoading = false).then(res => {
                this.listLoading = false
                if(!res.data.success) {
                    this.$message({
                        type: 'success',
                        message: '解锁成功!'
                    })
                    this.getRows()
                }

            })
        }).catch(() => {});
    }

    export default {
        data: data,
        methods: {
            getRows: getRows,
            save: save,
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