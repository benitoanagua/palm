import { newSpecPage } from '@stencil/core/testing';
import { PmDatePicker } from './date-picker';

describe('pm-date-picker', () => {
  it('accepts a typed ISO date and emits pmChange', async () => {
    const page = await newSpecPage({
      components: [PmDatePicker],
      html: '<pm-date-picker></pm-date-picker>',
    });
    const spy = jest.fn();
    page.root?.addEventListener('pmChange', (e: Event) => spy((e as CustomEvent<string>).detail));
    const input = page.root?.querySelector('input') as HTMLInputElement;
    input.value = '2026-03-15';
    input.dispatchEvent(new Event('input'));
    await page.waitForChanges();
    expect(spy).toHaveBeenCalledWith('2026-03-15');
  });

  it('disables dates outside the min/max range', async () => {
    const page = await newSpecPage({
      components: [PmDatePicker],
      html: '<pm-date-picker value="2026-03-15" min="2026-03-10" max="2026-03-20"></pm-date-picker>',
    });
    const instance = page.rootInstance as unknown as {
      isDisabled: (date: Date) => boolean;
    };
    expect(instance.isDisabled(new Date(2026, 2, 5))).toBe(true);
    expect(instance.isDisabled(new Date(2026, 2, 10))).toBe(false);
    expect(instance.isDisabled(new Date(2026, 2, 20))).toBe(false);
    expect(instance.isDisabled(new Date(2026, 2, 25))).toBe(true);
  });

  it('does not select a disabled day', async () => {
    const page = await newSpecPage({
      components: [PmDatePicker],
      html: '<pm-date-picker value="2026-03-15" min="2026-03-10"></pm-date-picker>',
    });
    const instance = page.rootInstance as unknown as {
      value: string;
      selectDay: (day: number) => void;
    };
    const before = instance.value;
    instance.selectDay(5);
    await page.waitForChanges();
    expect(instance.value).toBe(before);
  });
});
