({
    baseUrl: '../plugins',
    optimize: 'none',
    name: 'ligerui',
    packages: [
        {
            name: 'ligerui',
            location: '../core',
            main: 'base'
        }
    ],
    include:[
        '../core/inject',
        'ligerAccordion',
        'ligerButton',
        'ligerCheckBox',
        'ligerCheckBoxList',
        'ligerComboBox',
        'ligerDateEditor',
        'ligerDialog',
        'ligerDrag',
        'ligerEasyTab',
        'ligerFilter',
        'ligerForm',
        'ligerGrid',
        'ligerLayout',
        'ligerListBox',
        'ligerMenu',
        'ligerMenuBar',
        'ligerMessageBox',
        'ligerPanel',
        'ligerPopupEdit',
        'ligerPortal',
        'ligerRadio',
        'ligerRadioList',
        'ligerResizable',
        'ligerSpinner',
        'ligerTab',
        'ligerTextBox',
        'ligerTip',
        'ligerToolBar',
        'ligerTree',
        'ligerWindow'
        
    ],
    out: '../ligerui.all.js'
})
