import * as React from 'react';
import { useFixtureInput, useFixtureSelect } from 'react-cosmos/client';

export const Playground = () => {
  const [name, setName] = useFixtureInput('name', 'María García');
  const [size, setSize] = useFixtureSelect('size', {
    options: ['small', 'medium', 'large'],
    defaultValue: 'medium',
  });
  const [shape, setShape] = useFixtureSelect('shape', {
    options: ['circle', 'square'],
    defaultValue: 'circle',
  });
  const [status, setStatus] = useFixtureSelect('status', {
    options: ['online', 'offline', 'busy', 'away'],
    defaultValue: 'online',
  });
  return <pm-avatar name={name} size={size} shape={shape} status={status} />;
};

export const Default = () => <pm-avatar name="María García" status="online" />;
export const Offline = () => <pm-avatar name="Carlos Ruiz" status="offline" />;
export const Busy = () => <pm-avatar name="Ana López" size="large" shape="square" status="busy" />;
export const Away = () => <pm-avatar name="Luis Pérez" status="away" />;

export default { Playground, Default, Offline, Busy, Away };
