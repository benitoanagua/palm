import * as React from 'react';
import { useFixtureInput } from 'react-cosmos/client';

export const Playground = () => {
  const [value, setValue] = useFixtureInput('value', 50);
  const [min, setMin] = useFixtureInput('min', 0);
  const [max, setMax] = useFixtureInput('max', 100);
  const [step, setStep] = useFixtureInput('step', 1);
  const [disabled, setDisabled] = useFixtureInput('disabled', false);
  const [showValue, setShowValue] = useFixtureInput('showValue', false);
  const [label, setLabel] = useFixtureInput('label', 'Volumen');
  return (
    <pm-slider
      label={label}
      value={value}
      min={min}
      max={max}
      step={step}
      disabled={disabled}
      showValue={showValue}
    />
  );
};

export const Default = () => <pm-slider label="Volumen" value={50} min={0} max={100} />;
export const Disabled = () => <pm-slider label="Volumen" value={0} min={0} max={100} disabled />;
export const WithStep = () => (
  <pm-slider label="Volumen" value={25} min={0} max={100} step={5} showValue />
);
export const ShowValue = () => <pm-slider label="Volumen" value={75} min={0} max={100} showValue />;

export default { Playground, Default, Disabled, WithStep, ShowValue };
