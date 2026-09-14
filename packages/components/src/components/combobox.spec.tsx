import { newSpecPage } from '@stencil/core/testing';
import { PmCombobox } from './combobox';
import { PmComboboxOption } from './combobox-option';

describe('pm-combobox', () => {
  it('filters options as the user types', async () => {
    const page = await newSpecPage({ components: [PmCombobox, PmComboboxOption] });
    const combobox = page.doc.createElement('pm-combobox') as HTMLElement & PmCombobox;
    combobox.name = 'country';
    page.body.appendChild(combobox);
    ['us', 'ca', 'mx'].forEach((value, i) => {
      const option = page.doc.createElement('pm-combobox-option');
      option.setAttribute('value', value);
      option.textContent = ['United States', 'Canada', 'Mexico'][i];
      combobox.appendChild(option);
    });
    await page.waitForChanges();

    const input = combobox.querySelector('input') as HTMLInputElement;
    input.value = 'can';
    input.dispatchEvent(new Event('input'));
    await page.waitForChanges();

    const options = Array.from(combobox.querySelectorAll('pm-combobox-option'));
    const visible = options.filter((o) => o.hasAttribute('matched'));
    expect(visible.length).toBe(1);
    expect(visible[0].getAttribute('value')).toBe('ca');
  });

  it('emits pmChange when an option is selected', async () => {
    const page = await newSpecPage({ components: [PmCombobox, PmComboboxOption] });
    const combobox = page.doc.createElement('pm-combobox') as HTMLElement & PmCombobox;
    page.body.appendChild(combobox);
    const option = page.doc.createElement('pm-combobox-option');
    option.setAttribute('value', 'ca');
    option.textContent = 'Canada';
    combobox.appendChild(option);
    await page.waitForChanges();

    const spy = jest.fn();
    combobox.addEventListener('pmChange', (e: Event) => spy((e as CustomEvent<string>).detail));
    option.dispatchEvent(new CustomEvent('pmSelect', { detail: 'ca', bubbles: true }));
    await page.waitForChanges();
    expect(spy).toHaveBeenCalledWith('ca');
    expect(combobox.value).toBe('ca');
  });
});
