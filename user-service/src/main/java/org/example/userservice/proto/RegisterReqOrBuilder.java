// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: user.proto

package org.example.userservice.proto;

public interface RegisterReqOrBuilder extends
    // @@protoc_insertion_point(interface_extends:User.RegisterReq)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string email = 1;</code>
   * @return The email.
   */
  String getEmail();
  /**
   * <code>string email = 1;</code>
   * @return The bytes for email.
   */
  com.google.protobuf.ByteString
      getEmailBytes();

  /**
   * <code>string password = 2;</code>
   * @return The password.
   */
  String getPassword();
  /**
   * <code>string password = 2;</code>
   * @return The bytes for password.
   */
  com.google.protobuf.ByteString
      getPasswordBytes();

  /**
   * <code>string confirm_password = 3;</code>
   * @return The confirmPassword.
   */
  String getConfirmPassword();
  /**
   * <code>string confirm_password = 3;</code>
   * @return The bytes for confirmPassword.
   */
  com.google.protobuf.ByteString
      getConfirmPasswordBytes();
}
