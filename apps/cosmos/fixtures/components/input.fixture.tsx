import * as React from 'react';

export const Default = () => <pm-input label="Email" placeholder="you@example.com" />;
export const WithValue = () => <pm-input label="Email" value="user@example.com" />;
export const Disabled = () => <pm-input label="Email" disabled />;
export const Error = () => <pm-input label="Email" invalid error-text="Invalid email" />;

export default { Default, WithValue, Disabled, Error };
