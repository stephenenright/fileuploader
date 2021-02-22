import { useMemo } from 'react';

function useDateTimeFormat(options, locale) {
  const resolvedLocale = locale || 'default';
  return useMemo(
    () => new Intl.DateTimeFormat(resolvedLocale, options),
    [options, resolvedLocale]
  );
}

export default useDateTimeFormat;
