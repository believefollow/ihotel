jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { BookingtimeService } from '../service/bookingtime.service';
import { IBookingtime, Bookingtime } from '../bookingtime.model';

import { BookingtimeUpdateComponent } from './bookingtime-update.component';

describe('Component Tests', () => {
  describe('Bookingtime Management Update Component', () => {
    let comp: BookingtimeUpdateComponent;
    let fixture: ComponentFixture<BookingtimeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let bookingtimeService: BookingtimeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [BookingtimeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(BookingtimeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BookingtimeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      bookingtimeService = TestBed.inject(BookingtimeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const bookingtime: IBookingtime = { id: 456 };

        activatedRoute.data = of({ bookingtime });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(bookingtime));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const bookingtime = { id: 123 };
        spyOn(bookingtimeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ bookingtime });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: bookingtime }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(bookingtimeService.update).toHaveBeenCalledWith(bookingtime);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const bookingtime = new Bookingtime();
        spyOn(bookingtimeService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ bookingtime });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: bookingtime }));
        saveSubject.complete();

        // THEN
        expect(bookingtimeService.create).toHaveBeenCalledWith(bookingtime);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const bookingtime = { id: 123 };
        spyOn(bookingtimeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ bookingtime });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(bookingtimeService.update).toHaveBeenCalledWith(bookingtime);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
