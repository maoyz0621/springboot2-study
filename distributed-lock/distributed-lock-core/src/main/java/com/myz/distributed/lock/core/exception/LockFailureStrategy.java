/**
 * Copyright 2022 Inc.
 **/
package com.myz.distributed.lock.core.exception;

/**
 * @author maoyz0621 on 2022/5/22
 * @version v1.0
 */
public enum LockFailureStrategy implements LockFailureHandler {

    /**
     * 不做处理
     */
    NO_OPERATION() {
        @Override
        public void handleLockTimeOut() {

        }

        @Override
        public void handleReleaseTimeout() {

        }
    },

    /**
     * 快速失败
     */
    FAIL_FAST() {
        @Override
        public void handleLockTimeOut() {
            throw new LockFailureException("Lock Failure");
        }

        @Override
        public void handleReleaseTimeout() {
            throw new LockFailureException("Release Lock Failure");
        }
    },

    /**
     * 阻塞，一致获取，直到成功
     */
    KEEP_ACQUIRE() {
        @Override
        public void handleLockTimeOut() {

        }

        @Override
        public void handleReleaseTimeout() {

        }
    },
    ;
}