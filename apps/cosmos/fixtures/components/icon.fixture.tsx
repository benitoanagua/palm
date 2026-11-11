import * as React from 'react';
import { useFixtureInput } from 'react-cosmos/client';

export const Playground = () => {
  const [name, setName] = useFixtureInput('name', 'search');
  const [ariaLabel, setAriaLabel] = useFixtureInput('aria-label', 'Buscar');
  return <pm-icon name={name} aria-label={ariaLabel} />;
};

export const Search = () => <pm-icon name="search" />;
export const Settings = () => <pm-icon name="settings" />;
export const WithAriaLabel = () => <pm-icon name="search" aria-label="Buscar" />;
export const Decorative = () => <pm-icon name="star" aria-hidden="true" />;

export default { Playground, Search, Settings, WithAriaLabel, Decorative };
