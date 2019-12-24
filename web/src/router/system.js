import Main from '@/components/Main'
import Contract from '@/components/system/Contract'
import Notice from '@/components/system/Notice'
import AdminUser from '@/components/system/AdminUser'

const SystemRouter = [{
    path: '/system',
    name: '系统中心',
    component: Main,
    iconCls: 'fa fa-address-card',
    children: [
        {
            path: '/system/AdminUser',
            component: AdminUser,
            name: '用户管理'
        },
        {
            path: '/system/contract',
            component: Contract,
            name: '发布管理'
        },
        {
            path: '/system/Notice',
            component:Notice,
            name: '通告管理'
        },
    ]
}]

export {
    SystemRouter
}