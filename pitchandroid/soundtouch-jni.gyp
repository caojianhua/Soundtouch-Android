{
    'targets': [
    {
        'target_name': 'soundtouch-jni',
        'type':'shared_library',
        
        'include_dir':[
            './jni',
        ],
        
        
        'direct_dependent_settings':{
            'include_dirs':[
            
            ],
        },
        
        'export_dependent_settings':[
        ],
        
        'dependencies':[
            '<(DEPTH)/soundtouch/soundtouch.gyp:*',
        ],
        
        'sources':[
            './jni/com_example_soundtouchdemo_NativeSoundTouch.cpp',
        ],
    }
    
    ],

}