import * as React from 'react';
import { useFixtureInput, useFixtureSelect } from 'react-cosmos/client';

export const Playground = () => {
  const [variant, setVariant] = useFixtureSelect('variant', {
    options: ['text', 'circle', 'rect'],
    defaultValue: 'text',
  });
  const [width, setWidth] = useFixtureInput('width', '240px');
  const [height, setHeight] = useFixtureInput('height', '16px');
  return <pm-skeleton variant={variant} width={width} height={height} />;
};

export const Default = () => <pm-skeleton variant="text" width="240px" height="16px" />;
export const Circle = () => <pm-skeleton variant="circle" width="40px" height="40px" />;
export const Rect = () => <pm-skeleton variant="rect" width="120px" height="200px" />;
export const TextoLargo = () => <pm-skeleton variant="text" width="320px" height="20px" />;

export default { Playground, Default, Circle, Rect, TextoLargo };
