import * as dayjs from 'dayjs';

export interface ICheckCzl {
  id?: number;
  hoteltime?: dayjs.Dayjs;
  rtype?: string;
  rnum?: number;
  rOutnum?: number;
  czl?: number;
  chagrge?: number;
  chagrgeAvg?: number;
  empn?: string;
  entertime?: dayjs.Dayjs;
}

export class CheckCzl implements ICheckCzl {
  constructor(
    public id?: number,
    public hoteltime?: dayjs.Dayjs,
    public rtype?: string,
    public rnum?: number,
    public rOutnum?: number,
    public czl?: number,
    public chagrge?: number,
    public chagrgeAvg?: number,
    public empn?: string,
    public entertime?: dayjs.Dayjs
  ) {}
}

export function getCheckCzlIdentifier(checkCzl: ICheckCzl): number | undefined {
  return checkCzl.id;
}
