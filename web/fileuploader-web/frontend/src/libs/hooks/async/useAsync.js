import { useCallback, useEffect, useState } from 'react';

import {sanitizeError} from "../../error/ErrorLib";
import * as AsyncStatus from "./asyncStatus"

function abortify(promise, signal) {
  return new Promise((resolve, reject) => {
    promise.then(
      resolved => {
        if (!signal || !signal.aborted) {
          resolve(resolved);
        }
      },
      error => {
        if (error.name !== 'AbortError' || !signal || !signal.aborted) {
          reject(error);
        }
      }
    );
  });
}

function useAsync(effect, deps) {
  const [state, setState] = useState({
    data: null,
    error: null,
    attempts: 0,
    status: AsyncStatus.IDLE
  });

  useEffect(() => {
    const controller = new AbortController();
    const promise = effect(controller.signal);

    setState({
      ...state,
      data: null,
      error: null,
      status: promise ? AsyncStatus.PENDING : AsyncStatus.IDLE
    });

    if (promise) {
      abortify(promise, controller.signal)
        .then(data => {
          setState({
            ...state,
            data,
            error: null,
            status: AsyncStatus.RESOLVED
          });
        })
        .catch(error => {
          setState({
            ...state,
            error: sanitizeError(error),
            status: AsyncStatus.REJECTED
          });
        });
    }

    return () => {
      controller.abort();
      setState({
        ...state,
        data: null,
        error: null,
        status: AsyncStatus.IDLE
      });
    };
  }, [...(deps || []), state.attempts]);

  const isIdle = state.status === AsyncStatus.IDLE;
  const isPending = state.status === AsyncStatus.PENDING;
  const isResolved = state.status === AsyncStatus.RESOLVED;
  const isRejected = state.status === AsyncStatus.REJECTED;

  const retry = useCallback(
    () => setState(state => ({ ...state, attempts: state.attempts + 1 })),
    []
  );

  return {
    data: state.data,
    error: state.error,
    status: state.status,
    isIdle,
    isPending,
    isResolved,
    isRejected,
    retry
  };
}

export default useAsync;
