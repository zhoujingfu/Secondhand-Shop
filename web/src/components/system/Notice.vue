<template>
    <section>
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px; height: 100px;width: 99%;">
            <el-button size="" @click="save" style="margin-left: 40%;margin-top: 40px" >发布新通告</el-button>
        </el-col>

        <el-table :data="rows" highlight-current-row stripe border v-loading="listLoading" style="width: 99%;">
            <el-table-column prop="addedDate" :show-overflow-tooltip="true" label="发布时间" min-width="40" sortable="sortable" />
            <el-table-column prop="content" :show-overflow-tooltip="true" label="通告内容" min-width="200" sortable="sortable" />



            <el-table-column label="操作" width="180" align="center" fixed="right">
                <template slot-scope="scope">
                    <el-button size="small" @click="edit(scope.row)">修改</el-button>
                    <el-button size="small" type="danger" @click="remove(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog :title="title" width="80%" :visible.sync="formVisible" :close-on-click-modal="false">
            <el-form :model="form" label-width="120px" ref="form">
                <el-form-item label="通告内容">
                    <el-input v-model="form.content" auto-complete="off" />
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="formVisible = false">取消</el-button>
                <el-button type="success" @click="submit" :loading="loading">发布</el-button>
            </div>
        </el-dialog>
    </section>
</template>
<script>
    import {
        Save,
        GetAll,
        Remove
    } from '@/api/main/notice'

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
                message: '操作成功!'
            })
            this.getRows()
        })
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
                        type: 'error',
                        message: res.data.message
                    })
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