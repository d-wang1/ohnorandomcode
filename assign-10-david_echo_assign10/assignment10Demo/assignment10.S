#include <avr/io.h>
#include "asmMacros.s"  // Include macros to print log messages or a single register

.data
globalvar: .byte 0

.text


.global updateGlobal
updateGlobal:
  // TODO: void updateGlobal(byte a)
  ldi r30,lo8(globalvar)
  ldi r31,hi8(globalvar)
  st Z,r24
  ret


.global getGlobal
getGlobal:
  // TODO: uint8_t getGlobal()
  ldi r30,lo8(globalvar)
  ldi r31,hi8(globalvar)
  ld r24,Z
  ret


.global cStringLength
cStringLength://r24,25 reference address, r22 actual value, r23 null,r20,length,r18,1
  // TODO: uint8_t cStringLength(const char aString[])
  ldi r20,0
  ldi r18,1
  ldi r23,0x00
  mov r30,r24
  mov r31,r25
iterate_string:
  ld r22,Z+
  cp r22,r23
  breq findend
  add r20,r18
  jmp iterate_string
findend:
  mov r24,r20
  ret

.global sumArrays
sumArrays://r24 a to x, r22 b,r20 c to y, r18 length, r20 count, r24,r25 sum, r21 1, r22,0 ,r17 0
  push r16
  push r17
  push r26
  push r27
  push r28
  push r29
  push r30
  push r31
  mov r26,r24
  mov r27,r25//a to x
  mov r28,r22
  mov r29,r23//b to y
  mov r30,r20
  mov r31,r21//c to z
  ldi r20,0
  ldi r24,0
  ldi r25,0
  ldi r21,1
  ldi r17,0
iterate_array:
  cp r20,r18
  breq findend_sum
  ld r24,X+
  ld r16,Y+
  add r24,r16
  adc r25,r17
  st Z+,r24
  add r20,r21
  jmp iterate_array
findend_sum:
  mov r24,r30
  mov r25,r31
  pop r31
  pop r30
  pop r29
  pop r28
  pop r27
  pop r26
  pop r17
  pop r16
  ret

.global dot
dot://r24 a, r22 b, r20 length, r18 count, r19 0, r21 1, r24 r25 sum
  // TODO uint16_t dot(uint8_t *a, uint8_t *b, byte length)
  push r0
  push r1
  push r26
  push r27
  push r28
  push r29
  mov r26,r24
  mov r27,r25//a to x
  mov r28,r22
  mov r29,r23//b to y
  ldi r18,0
  ldi r21,1
  ldi r24,0
  ldi r25,0
iterate_array_dot:
  cp r20,r18
  breq findend_dot
  ld r22,X+
  ld r16,Y+
  mul r22,r16
  add r24,r0
  adc r25,r1
  add r18,r21
  jmp iterate_array_dot
findend_dot:
  pop r29
  pop r28
  pop r27
  pop r26
  pop r1
  pop r0
  ret
