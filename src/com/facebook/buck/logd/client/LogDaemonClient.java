/*
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.facebook.buck.logd.client;

import com.facebook.buck.logd.LogDaemonException;
import com.facebook.buck.logd.proto.LogMessage;
import com.facebook.buck.logd.proto.LogType;
import io.grpc.stub.StreamObserver;

/** Interface for LogD Client. */
public interface LogDaemonClient {

  /**
   * Client calls this method to request LogD to create a log file in file-system and/or storage.
   *
   * @return a file identifier {@code logFileId} corresponding to the created file by LogD server
   */
  int createLogFile(String logFilePath, LogType logType) throws LogDaemonException;

  /**
   * Client calls this method to acquire a StreamObserver object which listens for incoming logs.
   * Upon receiving the StreamObserver, client uses it to stream log messages to locations
   * identified by {@code logFileId}
   *
   * @param logFileId log file identifier generated by LogD server
   * @param logMessage log message to be streamed to LogD
   * @return a StreamObserver which observers and processes incoming logs from client
   */
  StreamObserver<LogMessage> openLog(int logFileId, String logMessage) throws LogDaemonException;

  /** close all existing channels to logD server */
  void shutdown();
}
