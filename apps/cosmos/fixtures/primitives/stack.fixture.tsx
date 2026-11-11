import * as React from 'react';
import { useFixtureSelect, useFixtureInput } from 'react-cosmos/client';

export const Playground = () => {
  const [direction, setDirection] = useFixtureSelect('direction', {
    options: ['row', 'column'],
    defaultValue: 'column',
  });
  const [gap, setGap] = useFixtureInput('gap', 'var(--pm-space-4)');
  const [align, setAlign] = useFixtureSelect('align', {
    options: ['start', 'center', 'end', 'stretch'],
    defaultValue: 'stretch',
  });
  return (
    <pm-stack direction={direction} gap={gap} align={align}>
      <pm-surface>Superficie 1</pm-surface>
      <pm-surface>Superficie 2</pm-surface>
      <pm-surface>Superficie 3</pm-surface>
    </pm-stack>
  );
};

export const Default = () => (
  <pm-stack gap="var(--pm-space-4)">
    <pm-surface>Contenido de superficie</pm-surface>
  </pm-stack>
);
export const WithGap = () => (
  <pm-stack gap="var(--pm-space-8)">
    <pm-surface>Superficie elevada</pm-surface>
    <pm-surface>Otra superficie</pm-surface>
  </pm-stack>
);

export default { Playground, Default, WithGap };
