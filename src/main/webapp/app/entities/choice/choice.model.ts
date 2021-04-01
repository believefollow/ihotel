import * as dayjs from 'dayjs';
import { IQuestion } from 'app/entities/question/question.model';

export interface IChoice {
  id?: number;
  createdat?: dayjs.Dayjs;
  updatedat?: dayjs.Dayjs;
  text?: string;
  votes?: number;
  question?: IQuestion;
}

export class Choice implements IChoice {
  constructor(
    public id?: number,
    public createdat?: dayjs.Dayjs,
    public updatedat?: dayjs.Dayjs,
    public text?: string,
    public votes?: number,
    public question?: IQuestion
  ) {}
}

export function getChoiceIdentifier(choice: IChoice): number | undefined {
  return choice.id;
}
