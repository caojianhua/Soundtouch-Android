{
  'target_defaults': {
    'conditions': [
      ['OS == "ios"', { 'defines': [ 'IOS' ]}],
      ['OS == "android"', { 'defines': [ 'ANDROID' ]}],
      ['OS == "linux"', { 'defines': [ 'LINUX' ]}],
    ],

    'default_configuration': 'Debug',
    # Building profile.
    # TODO(kyan): This is only a prototype, need enhance in real development env.
    'configurations': {
      'Common_Base': {
        'abstract': 1,
        'msvs_configuration_attributes': {
          # We use unicode character-set. 1 for unicode, 2 for multi-byte.
          'CharacterSet': '1',
        },
      },
      'Debug': {
        'inherit_from': ['Common_Base'],
      },
      'Release': {
        'inherit_from': ['Common_Base'],
      },
    },
  },
    'xcode_settings': {
        'GCC_VERSION': 'com.apple.compilers.llvm.clang.1_0',
        #'GCC_VERSION': 'com.apple.compilers.llvmgcc42',
        'SDKROOT': 'iphoneos',
				'ARCHS': '$(ARCHS_STANDARD_32_BIT)',
        'IPHONEOS_DEPLOYMENT_TARGET' : '5.0',
        # For iPhone, use 1
        # For iPad, use 2
        # For both, use 1,2
        # Default is 1, iphone
        'TARGETED_DEVICE_FAMILY': '1,2',

				'CLANG_CXX_LANGUAGE_STANDARD': 'gnu++0x',
				'CLANG_CXX_LIBRARY': 'libc++',
        'CLANG_ENABLE_OBJC_ARC': 'YES',
        'CLANG_WARN_EMPTY_BODY': 'YES',
        'CLANG_WARN_DUPLICATE_METHOD': 'YES',
      
				'ALWAYS_SEARCH_USER_PATHS': 'NO',
				'COPY_PHASE_STRIP': 'NO',

				'GCC_C_LANGUAGE_STANDARD': 'gnu99',
				'GCC_DYNAMIC_NO_PIC': 'NO',
				'GCC_GENERATE_DEBUGGING_SYMBOLS': 'YES',
				'GCC_INPUT_FILETYPE': 'automatic',
				'GCC_OPTIMIZATION_LEVEL': '0',
        'GCC_PREPROCESSOR_DEFINITIONS': [
          'DEBUG=1',
          '$(inherited)',
        ],
				#'GCC_PREPROCESSOR_DEFINITIONS': '( "DEBUG=1", "$(inherited)",)',
				'GCC_SYMBOLS_PRIVATE_EXTERN': 'NO',
        'GCC_WARN_ABOUT_RETURN_TYPE': 'YES',
        'GCC_WARN_UNINITIALIZED_AUTOS': 'YES',
        'GCC_WARN_UNUSED_VARIABLE': 'YES',

        'ONLY_ACTIVE_ARCH': 'YES',
        # In order to generate ad-hoc distribution.
        'SKIP_INSTALL': 'YES',
				#'LLVM_LTO': 'NO',
				#'GCC_PREPROCESSOR_DEFINITIONS': '__ARM_NEON__',
				#'OTHER_CFLAGS': '-D__ARM_NEON__ -mfpu=neon',
    }
}
