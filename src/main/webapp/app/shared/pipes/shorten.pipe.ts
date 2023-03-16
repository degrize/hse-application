import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'shorten',
})
export class ShortenPipe implements PipeTransform {
  transform(value: string | null | undefined, maxLength = 50): string {
    if (value !== null) {
      if (value !== undefined) {
        if (value.length <= maxLength) {
          return value;
        }
        return value.substring(0, maxLength) + '…';
      }
    }
    return '';
  }
}
