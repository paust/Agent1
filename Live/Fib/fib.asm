CompilerOracle: print Fib.fib
Compiled method (c1)      33   13       3       Fib::fib (26 bytes)
 total in heap  [0x00007f092110d090,0x00007f092110d938] = 2216
 relocation     [0x00007f092110d1b8,0x00007f092110d238] = 128
 main code      [0x00007f092110d240,0x00007f092110d600] = 960
 stub code      [0x00007f092110d600,0x00007f092110d6d8] = 216
 oops           [0x00007f092110d6d8,0x00007f092110d6e0] = 8
 metadata       [0x00007f092110d6e0,0x00007f092110d6e8] = 8
 scopes data    [0x00007f092110d6e8,0x00007f092110d7c0] = 216
 scopes pcs     [0x00007f092110d7c0,0x00007f092110d930] = 368
 dependencies   [0x00007f092110d930,0x00007f092110d938] = 8
Loaded disassembler from /usr/lib/jvm/java-8-oracle/jre/lib/amd64/hsdis-amd64.so
Decoding compiled method 0x00007f092110d090:
Code:
[Disassembling for mach='i386:x86-64']
[Entry Point]
[Verified Entry Point]
[Constants]
  # {method} {0x00007f0920454548} 'fib' '(I)J' in 'Fib'
  # parm0:    rsi       = int
  #           [sp+0x90]  (sp of caller)
  0x00007f092110d240: mov    %eax,-0x14000(%rsp)
  0x00007f092110d247: push   %rbp
  0x00007f092110d248: sub    $0x80,%rsp
  0x00007f092110d24f: mov    $0x7f0920454738,%rdi  ;   {metadata(method data for {method} {0x00007f0920454548} 'fib' '(I)J' in 'Fib')}
  0x00007f092110d259: mov    0xdc(%rdi),%ebx
  0x00007f092110d25f: add    $0x8,%ebx
  0x00007f092110d262: mov    %ebx,0xdc(%rdi)
  0x00007f092110d268: mov    $0x7f0920454548,%rdi  ;   {metadata({method} {0x00007f0920454548} 'fib' '(I)J' in 'Fib')}
  0x00007f092110d272: and    $0x1ff8,%ebx
  0x00007f092110d278: cmp    $0x0,%ebx
  0x00007f092110d27b: je     0x00007f092110d573  ;*iload_0
                                                ; - Fib::fib@0 (line 10)

  0x00007f092110d281: cmp    $0x0,%esi
  0x00007f092110d284: mov    $0x7f0920454738,%rdi  ;   {metadata(method data for {method} {0x00007f0920454548} 'fib' '(I)J' in 'Fib')}
  0x00007f092110d28e: mov    $0x108,%rbx
  0x00007f092110d298: jl     0x00007f092110d2a8
  0x00007f092110d29e: mov    $0x118,%rbx
  0x00007f092110d2a8: mov    (%rdi,%rbx,1),%rax
  0x00007f092110d2ac: lea    0x1(%rax),%rax
  0x00007f092110d2b0: mov    %rax,(%rdi,%rbx,1)
  0x00007f092110d2b4: jl     0x00007f092110d2f3  ;*iflt
                                                ; - Fib::fib@1 (line 10)

  0x00007f092110d2ba: cmp    $0x1,%esi
  0x00007f092110d2bd: mov    $0x7f0920454738,%rdi  ;   {metadata(method data for {method} {0x00007f0920454548} 'fib' '(I)J' in 'Fib')}
  0x00007f092110d2c7: mov    $0x128,%rbx
  0x00007f092110d2d1: jg     0x00007f092110d2e1
  0x00007f092110d2d7: mov    $0x138,%rbx
  0x00007f092110d2e1: mov    (%rdi,%rbx,1),%rax
  0x00007f092110d2e5: lea    0x1(%rax),%rax
  0x00007f092110d2e9: mov    %rax,(%rdi,%rbx,1)
  0x00007f092110d2ed: jle    0x00007f092110d55e  ;*if_icmpgt
                                                ; - Fib::fib@6 (line 10)

  0x00007f092110d2f3: mov    %rsi,%rdi
  0x00007f092110d2f6: sub    $0x2,%edi
  0x00007f092110d2f9: mov    $0x7f0920454738,%rbx  ;   {metadata(method data for {method} {0x00007f0920454548} 'fib' '(I)J' in 'Fib')}
  0x00007f092110d303: addq   $0x1,0x148(%rbx)
  0x00007f092110d30b: mov    $0x7f0920454738,%rbx  ;   {metadata(method data for {method} {0x00007f0920454548} 'fib' '(I)J' in 'Fib')}
  0x00007f092110d315: mov    0xdc(%rbx),%eax
  0x00007f092110d31b: add    $0x8,%eax
  0x00007f092110d31e: mov    %eax,0xdc(%rbx)
  0x00007f092110d324: mov    $0x7f0920454548,%rbx  ;   {metadata({method} {0x00007f0920454548} 'fib' '(I)J' in 'Fib')}
  0x00007f092110d32e: and    $0x7ffff8,%eax
  0x00007f092110d334: cmp    $0x0,%eax
  0x00007f092110d337: je     0x00007f092110d58a
  0x00007f092110d33d: cmp    $0x0,%edi
  0x00007f092110d340: mov    $0x7f0920454738,%rbx  ;   {metadata(method data for {method} {0x00007f0920454548} 'fib' '(I)J' in 'Fib')}
  0x00007f092110d34a: mov    $0x108,%rax
  0x00007f092110d354: jl     0x00007f092110d364
  0x00007f092110d35a: mov    $0x118,%rax
  0x00007f092110d364: mov    (%rbx,%rax,1),%rdx
  0x00007f092110d368: lea    0x1(%rdx),%rdx
  0x00007f092110d36c: mov    %rdx,(%rbx,%rax,1)
  0x00007f092110d370: jl     0x00007f092110d3af  ;*iflt
                                                ; - Fib::fib@1 (line 10)
                                                ; - Fib::fib@15 (line 13)

  0x00007f092110d376: cmp    $0x1,%edi
  0x00007f092110d379: mov    $0x7f0920454738,%rbx  ;   {metadata(method data for {method} {0x00007f0920454548} 'fib' '(I)J' in 'Fib')}
  0x00007f092110d383: mov    $0x128,%rax
  0x00007f092110d38d: jg     0x00007f092110d39d
  0x00007f092110d393: mov    $0x138,%rax
  0x00007f092110d39d: mov    (%rbx,%rax,1),%rdx
  0x00007f092110d3a1: lea    0x1(%rdx),%rdx
  0x00007f092110d3a5: mov    %rdx,(%rbx,%rax,1)
  0x00007f092110d3a9: jle    0x00007f092110d411  ;*if_icmpgt
                                                ; - Fib::fib@6 (line 10)
                                                ; - Fib::fib@15 (line 13)

  0x00007f092110d3af: mov    %esi,0x54(%rsp)
  0x00007f092110d3b3: mov    $0x7f0920454738,%rbx  ;   {metadata(method data for {method} {0x00007f0920454548} 'fib' '(I)J' in 'Fib')}
  0x00007f092110d3bd: addq   $0x1,0x148(%rbx)
  0x00007f092110d3c5: mov    %rdi,%rbx
  0x00007f092110d3c8: sub    $0x2,%ebx
  0x00007f092110d3cb: mov    %rbx,%rsi          ;*invokestatic fib
                                                ; - Fib::fib@15 (line 13)
                                                ; - Fib::fib@15 (line 13)

  0x00007f092110d3ce: mov    %edi,0x50(%rsp)
  0x00007f092110d3d2: nop    
  0x00007f092110d3d3: nop    
  0x00007f092110d3d4: nop    
  0x00007f092110d3d5: nop    
  0x00007f092110d3d6: nop    
  0x00007f092110d3d7: callq  0x00007f0921046160  ; OopMap{off=412}
                                                ;*invokestatic fib
                                                ; - Fib::fib@15 (line 13)
                                                ; - Fib::fib@15 (line 13)
                                                ;   {static_call}
  0x00007f092110d3dc: mov    $0x7f0920454738,%rsi  ;   {metadata(method data for {method} {0x00007f0920454548} 'fib' '(I)J' in 'Fib')}
  0x00007f092110d3e6: addq   $0x1,0x158(%rsi)
  0x00007f092110d3ee: mov    0x50(%rsp),%edi
  0x00007f092110d3f2: dec    %edi
  0x00007f092110d3f4: mov    %rdi,%rsi          ;*invokestatic fib
                                                ; - Fib::fib@21 (line 13)
                                                ; - Fib::fib@15 (line 13)

  0x00007f092110d3f7: mov    %rax,0x58(%rsp)
  0x00007f092110d3fc: nop    
  0x00007f092110d3fd: nop    
  0x00007f092110d3fe: nop    
  0x00007f092110d3ff: callq  0x00007f0921046160  ; OopMap{off=452}
                                                ;*invokestatic fib
                                                ; - Fib::fib@21 (line 13)
                                                ; - Fib::fib@15 (line 13)
                                                ;   {static_call}
  0x00007f092110d404: mov    0x58(%rsp),%rsi
  0x00007f092110d409: add    %rax,%rsi
  0x00007f092110d40c: jmpq   0x00007f092110d41b
  0x00007f092110d411: mov    %esi,0x54(%rsp)
  0x00007f092110d415: movslq %edi,%rdi
  0x00007f092110d418: mov    %rdi,%rsi          ;*iload_0
                                                ; - Fib::fib@18 (line 13)

  0x00007f092110d41b: mov    0x54(%rsp),%edi
  0x00007f092110d41f: dec    %edi
  0x00007f092110d421: mov    $0x7f0920454738,%rbx  ;   {metadata(method data for {method} {0x00007f0920454548} 'fib' '(I)J' in 'Fib')}
  0x00007f092110d42b: addq   $0x1,0x158(%rbx)
  0x00007f092110d433: mov    $0x7f0920454738,%rbx  ;   {metadata(method data for {method} {0x00007f0920454548} 'fib' '(I)J' in 'Fib')}
  0x00007f092110d43d: mov    0xdc(%rbx),%eax
  0x00007f092110d443: add    $0x8,%eax
  0x00007f092110d446: mov    %eax,0xdc(%rbx)
  0x00007f092110d44c: mov    $0x7f0920454548,%rbx  ;   {metadata({method} {0x00007f0920454548} 'fib' '(I)J' in 'Fib')}
  0x00007f092110d456: and    $0x7ffff8,%eax
  0x00007f092110d45c: cmp    $0x0,%eax
  0x00007f092110d45f: je     0x00007f092110d5a1
  0x00007f092110d465: cmp    $0x0,%edi
  0x00007f092110d468: mov    $0x7f0920454738,%rbx  ;   {metadata(method data for {method} {0x00007f0920454548} 'fib' '(I)J' in 'Fib')}
  0x00007f092110d472: mov    $0x108,%rax
  0x00007f092110d47c: jl     0x00007f092110d48c
  0x00007f092110d482: mov    $0x118,%rax
  0x00007f092110d48c: mov    (%rbx,%rax,1),%rdx
  0x00007f092110d490: lea    0x1(%rdx),%rdx
  0x00007f092110d494: mov    %rdx,(%rbx,%rax,1)
  0x00007f092110d498: jl     0x00007f092110d4d7  ;*iflt
                                                ; - Fib::fib@1 (line 10)
                                                ; - Fib::fib@21 (line 13)

  0x00007f092110d49e: cmp    $0x1,%edi
  0x00007f092110d4a1: mov    $0x7f0920454738,%rbx  ;   {metadata(method data for {method} {0x00007f0920454548} 'fib' '(I)J' in 'Fib')}
  0x00007f092110d4ab: mov    $0x128,%rax
  0x00007f092110d4b5: jg     0x00007f092110d4c5
  0x00007f092110d4bb: mov    $0x138,%rax
  0x00007f092110d4c5: mov    (%rbx,%rax,1),%rdx
  0x00007f092110d4c9: lea    0x1(%rdx),%rdx
  0x00007f092110d4cd: mov    %rdx,(%rbx,%rax,1)
  0x00007f092110d4d1: jle    0x00007f092110d539  ;*if_icmpgt
                                                ; - Fib::fib@6 (line 10)
                                                ; - Fib::fib@21 (line 13)

  0x00007f092110d4d7: mov    %rsi,0x68(%rsp)
  0x00007f092110d4dc: mov    $0x7f0920454738,%rbx  ;   {metadata(method data for {method} {0x00007f0920454548} 'fib' '(I)J' in 'Fib')}
  0x00007f092110d4e6: addq   $0x1,0x148(%rbx)
  0x00007f092110d4ee: mov    %rdi,%rbx
  0x00007f092110d4f1: sub    $0x2,%ebx
  0x00007f092110d4f4: mov    %rbx,%rsi          ;*invokestatic fib
                                                ; - Fib::fib@15 (line 13)
                                                ; - Fib::fib@21 (line 13)

  0x00007f092110d4f7: mov    %edi,0x60(%rsp)
  0x00007f092110d4fb: nop    
  0x00007f092110d4fc: nop    
  0x00007f092110d4fd: nop    
  0x00007f092110d4fe: nop    
  0x00007f092110d4ff: callq  0x00007f0921046160  ; OopMap{off=708}
                                                ;*invokestatic fib
                                                ; - Fib::fib@15 (line 13)
                                                ; - Fib::fib@21 (line 13)
                                                ;   {static_call}
  0x00007f092110d504: mov    $0x7f0920454738,%rsi  ;   {metadata(method data for {method} {0x00007f0920454548} 'fib' '(I)J' in 'Fib')}
  0x00007f092110d50e: addq   $0x1,0x158(%rsi)
  0x00007f092110d516: mov    0x60(%rsp),%edi
  0x00007f092110d51a: dec    %edi
  0x00007f092110d51c: mov    %rdi,%rsi          ;*invokestatic fib
                                                ; - Fib::fib@21 (line 13)
                                                ; - Fib::fib@21 (line 13)

  0x00007f092110d51f: mov    %rax,0x70(%rsp)
  0x00007f092110d524: nop    
  0x00007f092110d525: nop    
  0x00007f092110d526: nop    
  0x00007f092110d527: callq  0x00007f0921046160  ; OopMap{off=748}
                                                ;*invokestatic fib
                                                ; - Fib::fib@21 (line 13)
                                                ; - Fib::fib@21 (line 13)
                                                ;   {static_call}
  0x00007f092110d52c: mov    0x70(%rsp),%rsi
  0x00007f092110d531: add    %rax,%rsi
  0x00007f092110d534: jmpq   0x00007f092110d544
  0x00007f092110d539: mov    %rsi,0x68(%rsp)
  0x00007f092110d53e: movslq %edi,%rdi
  0x00007f092110d541: mov    %rdi,%rsi          ;*ladd
                                                ; - Fib::fib@24 (line 13)

  0x00007f092110d544: mov    0x68(%rsp),%rax
  0x00007f092110d549: add    %rax,%rsi
  0x00007f092110d54c: mov    %rsi,%rax
  0x00007f092110d54f: add    $0x80,%rsp
  0x00007f092110d556: pop    %rbp
  0x00007f092110d557: test   %eax,0x176f1ba3(%rip)        # 0x00007f09387ff100
                                                ;   {poll_return}
  0x00007f092110d55d: retq                      ;*lreturn
                                                ; - Fib::fib@25 (line 13)

  0x00007f092110d55e: movslq %esi,%rsi
  0x00007f092110d561: mov    %rsi,%rax
  0x00007f092110d564: add    $0x80,%rsp
  0x00007f092110d56b: pop    %rbp
  0x00007f092110d56c: test   %eax,0x176f1b8e(%rip)        # 0x00007f09387ff100
                                                ;   {poll_return}
  0x00007f092110d572: retq   
  0x00007f092110d573: mov    %rdi,0x8(%rsp)
  0x00007f092110d578: movq   $0xffffffffffffffff,(%rsp)
  0x00007f092110d580: callq  0x00007f09210fd9a0  ; OopMap{off=837}
                                                ;*synchronization entry
                                                ; - Fib::fib@-1 (line 10)
                                                ;   {runtime_call}
  0x00007f092110d585: jmpq   0x00007f092110d281
  0x00007f092110d58a: mov    %rbx,0x8(%rsp)
  0x00007f092110d58f: movq   $0xffffffffffffffff,(%rsp)
  0x00007f092110d597: callq  0x00007f09210fd9a0  ; OopMap{off=860}
                                                ;*synchronization entry
                                                ; - Fib::fib@-1 (line 10)
                                                ; - Fib::fib@15 (line 13)
                                                ;   {runtime_call}
  0x00007f092110d59c: jmpq   0x00007f092110d33d
  0x00007f092110d5a1: mov    %rbx,0x8(%rsp)
  0x00007f092110d5a6: movq   $0xffffffffffffffff,(%rsp)
  0x00007f092110d5ae: callq  0x00007f09210fd9a0  ; OopMap{off=883}
                                                ;*synchronization entry
                                                ; - Fib::fib@-1 (line 10)
                                                ; - Fib::fib@21 (line 13)
                                                ;   {runtime_call}
  0x00007f092110d5b3: jmpq   0x00007f092110d465
  0x00007f092110d5b8: nop    
  0x00007f092110d5b9: nop    
  0x00007f092110d5ba: mov    0x298(%r15),%rax
  0x00007f092110d5c1: mov    $0x0,%r10
  0x00007f092110d5cb: mov    %r10,0x298(%r15)
  0x00007f092110d5d2: mov    $0x0,%r10
  0x00007f092110d5dc: mov    %r10,0x2a0(%r15)
  0x00007f092110d5e3: add    $0x80,%rsp
  0x00007f092110d5ea: pop    %rbp
  0x00007f092110d5eb: jmpq   0x00007f092106be20  ;   {runtime_call}
  0x00007f092110d5f0: hlt    
  0x00007f092110d5f1: hlt    
  0x00007f092110d5f2: hlt    
  0x00007f092110d5f3: hlt    
  0x00007f092110d5f4: hlt    
  0x00007f092110d5f5: hlt    
  0x00007f092110d5f6: hlt    
  0x00007f092110d5f7: hlt    
  0x00007f092110d5f8: hlt    
  0x00007f092110d5f9: hlt    
  0x00007f092110d5fa: hlt    
  0x00007f092110d5fb: hlt    
  0x00007f092110d5fc: hlt    
  0x00007f092110d5fd: hlt    
  0x00007f092110d5fe: hlt    
  0x00007f092110d5ff: hlt    
[Stub Code]
  0x00007f092110d600: nop                       ;   {no_reloc}
  0x00007f092110d601: nop    
  0x00007f092110d602: nop    
  0x00007f092110d603: nop    
  0x00007f092110d604: nop    
  0x00007f092110d605: mov    $0x0,%rbx          ;   {static_stub}
  0x00007f092110d60f: jmpq   0x00007f092110d60f  ;   {runtime_call}
  0x00007f092110d614: nop    
  0x00007f092110d615: mov    $0x0,%rbx          ;   {static_stub}
  0x00007f092110d61f: jmpq   0x00007f092110d61f  ;   {runtime_call}
  0x00007f092110d624: nop    
  0x00007f092110d625: mov    $0x0,%rbx          ;   {static_stub}
  0x00007f092110d62f: jmpq   0x00007f092110d62f  ;   {runtime_call}
  0x00007f092110d634: nop    
  0x00007f092110d635: mov    $0x0,%rbx          ;   {static_stub}
  0x00007f092110d63f: jmpq   0x00007f092110d63f  ;   {runtime_call}
[Exception Handler]
  0x00007f092110d644: callq  0x00007f09210fb0e0  ;   {runtime_call}
  0x00007f092110d649: mov    %rsp,-0x28(%rsp)
  0x00007f092110d64e: sub    $0x80,%rsp
  0x00007f092110d655: mov    %rax,0x78(%rsp)
  0x00007f092110d65a: mov    %rcx,0x70(%rsp)
  0x00007f092110d65f: mov    %rdx,0x68(%rsp)
  0x00007f092110d664: mov    %rbx,0x60(%rsp)
  0x00007f092110d669: mov    %rbp,0x50(%rsp)
  0x00007f092110d66e: mov    %rsi,0x48(%rsp)
  0x00007f092110d673: mov    %rdi,0x40(%rsp)
  0x00007f092110d678: mov    %r8,0x38(%rsp)
  0x00007f092110d67d: mov    %r9,0x30(%rsp)
  0x00007f092110d682: mov    %r10,0x28(%rsp)
  0x00007f092110d687: mov    %r11,0x20(%rsp)
  0x00007f092110d68c: mov    %r12,0x18(%rsp)
  0x00007f092110d691: mov    %r13,0x10(%rsp)
  0x00007f092110d696: mov    %r14,0x8(%rsp)
  0x00007f092110d69b: mov    %r15,(%rsp)
  0x00007f092110d69f: mov    $0x7f093772b3e6,%rdi  ;   {external_word}
  0x00007f092110d6a9: mov    $0x7f092110d649,%rsi  ;   {internal_word}
  0x00007f092110d6b3: mov    %rsp,%rdx
  0x00007f092110d6b6: and    $0xfffffffffffffff0,%rsp
  0x00007f092110d6ba: callq  0x00007f093745b600  ;   {runtime_call}
  0x00007f092110d6bf: hlt    
[Deopt Handler Code]
  0x00007f092110d6c0: mov    $0x7f092110d6c0,%r10  ;   {section_word}
  0x00007f092110d6ca: push   %r10
  0x00007f092110d6cc: jmpq   0x00007f0921047100  ;   {runtime_call}
  0x00007f092110d6d1: hlt    
  0x00007f092110d6d2: hlt    
  0x00007f092110d6d3: hlt    
  0x00007f092110d6d4: hlt    
  0x00007f092110d6d5: hlt    
  0x00007f092110d6d6: hlt    
  0x00007f092110d6d7: hlt    
OopMapSet contains 7 OopMaps

#0 
OopMap{off=412}
#1 
OopMap{off=452}
#2 
OopMap{off=708}
#3 
OopMap{off=748}
#4 
OopMap{off=837}
#5 
OopMap{off=860}
#6 
OopMap{off=883}
Compiled method (c2)      38   14       4       Fib::fib (26 bytes)
 total in heap  [0x00007f092110a910,0x00007f092110ae38] = 1320
 relocation     [0x00007f092110aa38,0x00007f092110aa78] = 64
 main code      [0x00007f092110aa80,0x00007f092110ab40] = 192
 stub code      [0x00007f092110ab40,0x00007f092110ab90] = 80
 oops           [0x00007f092110ab90,0x00007f092110ab98] = 8
 metadata       [0x00007f092110ab98,0x00007f092110aba0] = 8
 scopes data    [0x00007f092110aba0,0x00007f092110ac40] = 160
 scopes pcs     [0x00007f092110ac40,0x00007f092110add0] = 400
 dependencies   [0x00007f092110add0,0x00007f092110add8] = 8
 handler table  [0x00007f092110add8,0x00007f092110ae38] = 96
Decoding compiled method 0x00007f092110a910:
Code:
[Entry Point]
[Verified Entry Point]
[Constants]
  # {method} {0x00007f0920454548} 'fib' '(I)J' in 'Fib'
  # parm0:    rsi       = int
  #           [sp+0x30]  (sp of caller)
  0x00007f092110aa80: mov    %eax,-0x14000(%rsp)
  0x00007f092110aa87: push   %rbp
  0x00007f092110aa88: sub    $0x20,%rsp         ;*synchronization entry
                                                ; - Fib::fib@-1 (line 10)

  0x00007f092110aa8c: mov    %esi,%ebp
  0x00007f092110aa8e: cmp    $0x2,%esi
  0x00007f092110aa91: jb     0x00007f092110ab07  ;*if_icmpgt
                                                ; - Fib::fib@6 (line 10)

  0x00007f092110aa93: mov    %esi,%r11d
  0x00007f092110aa96: add    $0xfffffffffffffffe,%r11d
                                                ;*isub
                                                ; - Fib::fib@14 (line 13)

  0x00007f092110aa9a: mov    %r11d,(%rsp)
  0x00007f092110aa9e: mov    %esi,%r10d
  0x00007f092110aaa1: add    $0xfffffffffffffffd,%r10d
                                                ;*isub
                                                ; - Fib::fib@20 (line 13)
                                                ; - Fib::fib@15 (line 13)

  0x00007f092110aaa5: mov    %r10d,0x4(%rsp)
  0x00007f092110aaaa: cmp    $0x2,%r11d
  0x00007f092110aaae: jb     0x00007f092110aad4  ;*if_icmpgt
                                                ; - Fib::fib@6 (line 10)
                                                ; - Fib::fib@15 (line 13)

  0x00007f092110aab0: add    $0xfffffffffffffffc,%esi
  0x00007f092110aab3: callq  0x00007f0921046160  ; OopMap{off=56}
                                                ;*invokestatic fib
                                                ; - Fib::fib@15 (line 13)
                                                ; - Fib::fib@15 (line 13)
                                                ;   {static_call}
  0x00007f092110aab8: mov    %rax,0x8(%rsp)
  0x00007f092110aabd: mov    0x4(%rsp),%esi
  0x00007f092110aac1: xchg   %ax,%ax
  0x00007f092110aac3: callq  0x00007f0921046160  ; OopMap{off=72}
                                                ;*invokestatic fib
                                                ; - Fib::fib@21 (line 13)
                                                ; - Fib::fib@15 (line 13)
                                                ;   {static_call}
  0x00007f092110aac8: add    0x8(%rsp),%rax     ;*ladd
                                                ; - Fib::fib@24 (line 13)
                                                ; - Fib::fib@15 (line 13)

  0x00007f092110aacd: mov    %rax,0x8(%rsp)
  0x00007f092110aad2: jmp    0x00007f092110aadc
  0x00007f092110aad4: movslq %r11d,%r10         ;*i2l  ; - Fib::fib@10 (line 11)
                                                ; - Fib::fib@15 (line 13)

  0x00007f092110aad7: mov    %r10,0x8(%rsp)     ;*invokestatic fib
                                                ; - Fib::fib@15 (line 13)

  0x00007f092110aadc: dec    %ebp               ;*isub
                                                ; - Fib::fib@20 (line 13)

  0x00007f092110aade: cmp    $0x2,%ebp
  0x00007f092110aae1: jb     0x00007f092110aafd  ;*if_icmpgt
                                                ; - Fib::fib@6 (line 10)
                                                ; - Fib::fib@21 (line 13)

  0x00007f092110aae3: mov    0x4(%rsp),%esi
  0x00007f092110aae7: callq  0x00007f0921046160  ; OopMap{off=108}
                                                ;*invokestatic fib
                                                ; - Fib::fib@15 (line 13)
                                                ; - Fib::fib@21 (line 13)
                                                ;   {static_call}
  0x00007f092110aaec: mov    %rax,%rbp
  0x00007f092110aaef: mov    (%rsp),%esi
  0x00007f092110aaf2: nop    
  0x00007f092110aaf3: callq  0x00007f0921046160  ; OopMap{off=120}
                                                ;*invokestatic fib
                                                ; - Fib::fib@21 (line 13)
                                                ; - Fib::fib@21 (line 13)
                                                ;   {static_call}
  0x00007f092110aaf8: add    %rbp,%rax          ;*ladd
                                                ; - Fib::fib@24 (line 13)
                                                ; - Fib::fib@21 (line 13)

  0x00007f092110aafb: jmp    0x00007f092110ab00
  0x00007f092110aafd: movslq %ebp,%rax          ;*invokestatic fib
                                                ; - Fib::fib@21 (line 13)

  0x00007f092110ab00: add    0x8(%rsp),%rax     ;*ladd
                                                ; - Fib::fib@24 (line 13)

  0x00007f092110ab05: jmp    0x00007f092110ab0a
  0x00007f092110ab07: movslq %esi,%rax          ;*if_icmpgt
                                                ; - Fib::fib@6 (line 10)
                                                ; - Fib::fib@15 (line 13)

  0x00007f092110ab0a: add    $0x20,%rsp
  0x00007f092110ab0e: pop    %rbp
  0x00007f092110ab0f: test   %eax,0x176f44eb(%rip)        # 0x00007f09387ff000
                                                ;   {poll_return}
  0x00007f092110ab15: retq                      ;*invokestatic fib
                                                ; - Fib::fib@15 (line 13)
                                                ; - Fib::fib@15 (line 13)

  0x00007f092110ab16: mov    %rax,%rsi
  0x00007f092110ab19: jmp    0x00007f092110ab28  ;*invokestatic fib
                                                ; - Fib::fib@15 (line 13)
                                                ; - Fib::fib@21 (line 13)

  0x00007f092110ab1b: mov    %rax,%rsi
  0x00007f092110ab1e: jmp    0x00007f092110ab28  ;*invokestatic fib
                                                ; - Fib::fib@21 (line 13)
                                                ; - Fib::fib@15 (line 13)

  0x00007f092110ab20: mov    %rax,%rsi
  0x00007f092110ab23: jmp    0x00007f092110ab28  ;*invokestatic fib
                                                ; - Fib::fib@21 (line 13)
                                                ; - Fib::fib@21 (line 13)

  0x00007f092110ab25: mov    %rax,%rsi          ;*invokestatic fib
                                                ; - Fib::fib@21 (line 13)
                                                ; - Fib::fib@15 (line 13)

  0x00007f092110ab28: add    $0x20,%rsp
  0x00007f092110ab2c: pop    %rbp
  0x00007f092110ab2d: jmpq   0x00007f09211010a0  ;   {runtime_call}
  0x00007f092110ab32: hlt    
  0x00007f092110ab33: hlt    
  0x00007f092110ab34: hlt    
  0x00007f092110ab35: hlt    
  0x00007f092110ab36: hlt    
  0x00007f092110ab37: hlt    
  0x00007f092110ab38: hlt    
  0x00007f092110ab39: hlt    
  0x00007f092110ab3a: hlt    
  0x00007f092110ab3b: hlt    
  0x00007f092110ab3c: hlt    
  0x00007f092110ab3d: hlt    
  0x00007f092110ab3e: hlt    
  0x00007f092110ab3f: hlt    
[Stub Code]
  0x00007f092110ab40: mov    $0x0,%rbx          ;   {no_reloc}
  0x00007f092110ab4a: jmpq   0x00007f092110ab4a  ;   {runtime_call}
  0x00007f092110ab4f: mov    $0x0,%rbx          ;   {static_stub}
  0x00007f092110ab59: jmpq   0x00007f092110ab59  ;   {runtime_call}
  0x00007f092110ab5e: mov    $0x0,%rbx          ;   {static_stub}
  0x00007f092110ab68: jmpq   0x00007f092110ab68  ;   {runtime_call}
  0x00007f092110ab6d: mov    $0x0,%rbx          ;   {static_stub}
  0x00007f092110ab77: jmpq   0x00007f092110ab77  ;   {runtime_call}
[Exception Handler]
  0x00007f092110ab7c: jmpq   0x00007f092106c0e0  ;   {runtime_call}
[Deopt Handler Code]
  0x00007f092110ab81: callq  0x00007f092110ab86
  0x00007f092110ab86: subq   $0x5,(%rsp)
  0x00007f092110ab8b: jmpq   0x00007f0921047100  ;   {runtime_call}
OopMapSet contains 4 OopMaps

#0 
OopMap{off=56}
#1 
OopMap{off=72}
#2 
OopMap{off=108}
#3 
OopMap{off=120}
102334155
