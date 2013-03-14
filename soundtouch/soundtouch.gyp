{
    'targets': [
    {
        'target_name':'soundtouch',
        'type':'<(library)',
        
        'include_dirs': [
            './include/',
            './source/SoundTouch',
        ],
        
        'defines': [
            'ST_NO_EXCEPTION_HANDLING',
        ],
        
        'direct_dependent_settings': {
            'include_dirs': [
                './include',
                './source/SoundTouch',
            ],
            
            'defines': [
                'ST_NO_EXCEPTION_HANDLING',
            ],
            
        },
        
        
        'sources':[
            './source/SoundTouch/AAFilter.cpp',
            './source/SoundTouch/FIFOSampleBuffer.cpp',
            './source/SoundTouch/FIRFilter.cpp',
            './source/SoundTouch/cpu_detect_x86.cpp',
            './source/SoundTouch/RateTransposer.cpp',
            './source/SoundTouch/SoundTouch.cpp',
            './source/SoundTouch/TDStretch.cpp',
            './source/SoundTouch/BPMDetect.cpp',
            './source/SoundTouch/PeakFinder.cpp',
        ],
    }],
}