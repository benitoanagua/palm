import * as React from 'react';

export const Default = () => <pm-avatar name="María García" status="online" />;
export const Offline = () => (
  <pm-avatar name="Carlos Ruiz" src="https://example.com/avatar.png" status="offline" />
);
export const Busy = () => <pm-avatar name="Ana López" size="large" shape="square" status="busy" />;

export default { Default, Offline, Busy };
