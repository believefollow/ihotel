import * as dayjs from 'dayjs';

export interface ICheckCzl2 {
  id?: number;
  hoteltime?: dayjs.Dayjs;
  protocol?: string;
  rnum?: number;
  czl?: number;
  chagrge?: number;
  chagrgeAvg?: number;
  empn?: string;
  entertime?: dayjs.Dayjs;
}

export class CheckCzl2 implements ICheckCzl2 {
  constructor(
    public id?: number,
    public hoteltime?: dayjs.Dayjs,
    public protocol?: string,
    public rnum?: number,
    public czl?: number,
    public chagrge?: number,
    public chagrgeAvg?: number,
    public empn?: string,
    public entertime?: dayjs.Dayjs
  ) {}
}

export function getCheckCzl2Identifier(checkCzl2: ICheckCzl2): number | undefined {
  return checkCzl2.id;
}
