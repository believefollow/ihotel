jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AccPpService } from '../service/acc-pp.service';
import { IAccPp, AccPp } from '../acc-pp.model';

import { AccPpUpdateComponent } from './acc-pp-update.component';

describe('Component Tests', () => {
  describe('AccPp Management Update Component', () => {
    let comp: AccPpUpdateComponent;
    let fixture: ComponentFixture<AccPpUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let accPpService: AccPpService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AccPpUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AccPpUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccPpUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      accPpService = TestBed.inject(AccPpService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const accPp: IAccPp = { id: 456 };

        activatedRoute.data = of({ accPp });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(accPp));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const accPp = { id: 123 };
        spyOn(accPpService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ accPp });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: accPp }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(accPpService.update).toHaveBeenCalledWith(accPp);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const accPp = new AccPp();
        spyOn(accPpService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ accPp });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: accPp }));
        saveSubject.complete();

        // THEN
        expect(accPpService.create).toHaveBeenCalledWith(accPp);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const accPp = { id: 123 };
        spyOn(accPpService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ accPp });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(accPpService.update).toHaveBeenCalledWith(accPp);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
