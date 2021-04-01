jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { BookYstService } from '../service/book-yst.service';
import { IBookYst, BookYst } from '../book-yst.model';

import { BookYstUpdateComponent } from './book-yst-update.component';

describe('Component Tests', () => {
  describe('BookYst Management Update Component', () => {
    let comp: BookYstUpdateComponent;
    let fixture: ComponentFixture<BookYstUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let bookYstService: BookYstService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [BookYstUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(BookYstUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BookYstUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      bookYstService = TestBed.inject(BookYstService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const bookYst: IBookYst = { id: 456 };

        activatedRoute.data = of({ bookYst });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(bookYst));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const bookYst = { id: 123 };
        spyOn(bookYstService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ bookYst });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: bookYst }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(bookYstService.update).toHaveBeenCalledWith(bookYst);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const bookYst = new BookYst();
        spyOn(bookYstService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ bookYst });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: bookYst }));
        saveSubject.complete();

        // THEN
        expect(bookYstService.create).toHaveBeenCalledWith(bookYst);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const bookYst = { id: 123 };
        spyOn(bookYstService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ bookYst });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(bookYstService.update).toHaveBeenCalledWith(bookYst);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
